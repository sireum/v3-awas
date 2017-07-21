/*
  Jquery SVG Pan & Zoom v1.1, February 2017

  Author : AvcS (avcs06@gmail.com)
  Repository: https://github.com/avcs06/Jquery-Svg-Pan-Zoom/

  Copyright (C) 2015 avcs06
  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
  The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/

//Polyfill for AnimationFrame
(function() {
  var lastTime = 0;
  var vendors = ['webkit', 'moz', 'o', 'ms'];
  for (var x = 0; x < vendors.length && !window.requestAnimationFrame; ++x) {
    window.requestAnimationFrame = window[vendors[x] + 'RequestAnimationFrame'];
    window.cancelAnimationFrame = window[vendors[x] + 'CancelAnimationFrame'] || window[vendors[x] + 'CancelRequestAnimationFrame'];
  }

  if (!window.requestAnimationFrame)
    window.requestAnimationFrame = function(callback, element) {
      var currTime = new Date().getTime();
      var timeToCall = Math.max(0, 16 - (currTime - lastTime));
      var id = window.setTimeout(function() {
          callback(currTime + timeToCall);
        },
        timeToCall);
      lastTime = currTime + timeToCall;
      return id;
    };

  if (!window.cancelAnimationFrame)
    window.cancelAnimationFrame = function(id) {
      clearTimeout(id);
    };
}());

(function() {
  (function($) {
    var defaultOptions = {
      events: {
        mouseWheel: true,
        doubleClick: true,
        drag: true,
        dragMouseButton : 1,
        dragCursor: "move",
      },
      animationTime: 300,
      zoomFactor: 0.25,
      maxZoom: 3,
      panFactor: 100,
      initialViewBox: null,
      limits: null,
      eventMagnet: null,
      onZoom : function(zoomFactor) {}
    };

    var defaultViewBox = {
      x: 0,
      y: 0,
      width: 1000,
      height: 1000
    };

    /**
     * Check the limits of the viewbox, modifies the viewbox to respect the limits while keeping
     * the original view box size if possible. If the viewbox needs to be reduced, the modified
     * viewbox will keep the aspect ratio of the original viewbox.
     *
     * @param {Object} viewBox
     *   The original view box. Takes numbers, in the format `{x, y, width, height}`.
     *
     * @param {Object} limits
     *   Extents which can be shown, in the viewbox coordinate system. Takes numbers in the format
     *   `{x, y, x2, y2}`.
     */
    var checkLimits = function(vb, limits) {
      var limitsWidth = Math.abs(limits.x2 - limits.x);
      var limitsHeight = Math.abs(limits.y2 - limits.y);

      if (vb.width > limitsWidth) {
        vb.height *= limitsWidth / vb.width;
        vb.width = limitsWidth;
      }

      if (vb.height > limitsHeight) {
        vb.width *= limitsHeight / vb.height;
        vb.height = limitsHeight;
      }

      if (vb.x < limits.x) vb.x = limits.x;
      if (vb.y < limits.y) vb.y = limits.y;
      if (vb.x + vb.width > limits.x2) vb.x = limits.x2 - vb.width;
      if (vb.y + vb.height > limits.y2) vb.y = limits.y2 - vb.height;
    };

    /**
     * Parse the viewbox string as defined in the spec for the svg tag.
     *
     * @param {String} viewBoxString
     *   A valid value of the `viewBox` attribute.
     *
     * @return {Object} viewBox
     *   A viewbox object. Contains numbers, in the format `{x, y, width, height}`.
     */
    var parseViewBoxString = function(string) {
      var vb = string.replace("\s+", " ").split(" ");
      return vb = {
        x: parseFloat(vb[0]),
        y: parseFloat(vb[1]),
        width: parseFloat(vb[2]),
        height: parseFloat(vb[3])
      };
    };

    /**
     * Transform the point from page co-ordinate system to SVG co-ordinate system
     *
     * @param {SVGElement} svgRoot
     *   The `<svg>` DOM object
     *
     * @param {Object} point
     *   Coordinates of the point. Contains numbers, in the format `{x, y}`.
     *
     * @return {Object}
     *   Coordinates of the point in SVG co-ordinate system. Contains numbers, in the format `{x, y}`.
     */
    var coordinateTransform = function(svgRoot, point) {
      var pos = svgRoot.createSVGPoint();
      pos.x = parseInt(point.x, 10);
      pos.y = parseInt(point.y, 10);
      pos = pos.matrixTransform(svgRoot.getScreenCTM().inverse());
      return pos;
    };

    /**
     * Get the mouse or first touch position from the `event`, relative to the SVG viewBox.
     *
     * @param {SVGElement} svgRoot
     *   The `<svg>` DOM object
     *
     * @param {MouseEvent|TouchEvent|jQueryEvent} event
     *   The DOM or jQuery event.
     *
     * @return {Object}
     *   Coordinates of the event. Contains numbers, in the format `{x, y}`.
     */
    var getViewBoxCoordinatesFromEvent = function(svgRoot, e) {
      var position = {
        x: null,
        y: null
      };

      if(/touch/i.test(e.type)) {
          //Event has touch information
          if(e.touches != null && e.touches.length) {
              e = e.touches[0];
          }
          //If modified event get original event
          else if(e.originalEvent != null && e.originalEvent.touches.length) {
              e = e.originalEvent.touches[0];
          }
          //If touchend get the required info from changedTouches
          else if(e.changedTouches != null && e.changedTouches.length) {
              e = e.changedTouches[0];
          }
      } else if(e.clientX == null && e.originalEvent != null) {
          e = e.originalEvent;
      }

      position.x = e.clientX;
      position.y = e.clientY;

      return coordinateTransform(svgRoot, position);
    };

    /**
     * Create and set viewBox attribute of given SVG element
     *
     * @param {SVGElement} svg
     *   The `<svg>` DOM object
     *
     * @param {Object} viewBox
     *   A viewbox object. Contains numbers, in the format `{x, y, width, height}`.
     */
    var setViewBox = function(svg,viewBox) {
      svg.setAttribute("viewBox", viewBox.x + " " + viewBox.y + " " + viewBox.width + " " + viewBox.height);
    };

    /**
     * Get distance between fingers for two finger touch event
     *
     * @param {TouchEvent|jQueryEvent} event
     *   The DOM or jQuery event.
     */
    var touchDistance = function(event) {
      var touches = ((event.originalEvent != null) && (event.touches == null)) ? event.originalEvent.touches : event.touches;
      return Math.sqrt((touches[0].clientX - touches[1].clientX) * (touches[0].clientX - touches[1].clientX) + (touches[0].clientY - touches[1].clientY) * (touches[0].clientY - touches[1].clientY));
    };

    /**
     * Check if the event is a two finger touch event
     *
     * @param {TouchEvent|jQueryEvent} event
     *   The DOM or jQuery event.
     */
    var isDoubleTouch = function(event) {
      var touches = ((event.originalEvent != null) && (event.touches == null)) ? event.originalEvent.touches : event.touches;
      return touches.length == 2;
    };

    /**
     * Get mid point of fingers for two finger touch event in SVG co-ordinate system
     *
     * @param {SVGElement} svg
     *   The `<svg>` DOM object
     *
     * @param {TouchEvent|jQueryEvent} event
     *   The DOM or jQuery event.
     */
    var touchCenter = function(svg,event) {
      var touches = ((event.originalEvent != null) && (event.touches == null)) ? event.originalEvent.touches : event.touches;
      return coordinateTransform(svg, {
        x: (touches[0].clientX + touches[1].clientX) / 2,
        y: (touches[0].clientY + touches[1].clientY) / 2
      });
    };

    return $.fn.svgPanZoom = function(options) {
      var ret = [];
      this.each(function() {
        var that = this;
        var opts, viewBox;

        that.setAttribute("preserveAspectRatio", "xMidYMid meet");
        viewBox = $.extend({}, that.viewBox.baseVal);
        opts = $.extend(true, {}, defaultOptions, options, {svg: that});

        //Set default values if not set
        opts.animationTime = opts.animationTime || 0;
        viewBox.x = viewBox.x || 0;
        viewBox.y = viewBox.y || 0;
        viewBox.width = viewBox.width || 0;
        viewBox.height = viewBox.height || 0;

        if (opts.initialViewBox != null) {
          if (typeof opts.initialViewBox === "string") {
            viewBox = parseViewBoxString(opts.initialViewBox);
          } else if (typeof opts.initialViewBox === "object") {
            viewBox = $.extend({}, defaultViewBox, opts.initialViewBox);
          } else {
            throw "initialViewBox is of invalid type";
          }
        } else if (viewBox.x === 0 && viewBox.y === 0 && viewBox.width === 0 && viewBox.height === 0) {
          viewBox = defaultViewBox;
        }
        opts.initialViewBox = $.extend({}, viewBox);

        (function() {
          if (opts.limits == null) {
            var horizontalSizeIncrement = viewBox.width * 0.15;
            var verticalSizeIncrement = viewBox.height * 0.15;
            opts.limits = {
              x: viewBox.x - horizontalSizeIncrement,
              y: viewBox.y - verticalSizeIncrement,
              x2: viewBox.x + viewBox.width + horizontalSizeIncrement,
              y2: viewBox.y + viewBox.height + verticalSizeIncrement
            };
          }
        })();

        var animate = (function(){
          var currentAnimation;

          function Animation(svg,initialObject,finalObject,time) {
            var start= 0,now = 0,that = this;

            this.getCurrent = function() {
              var box = {};
              $.each(initialObject,function(key,value){
                box[key] = initialObject[key] + (now / time) * (finalObject[key] - initialObject[key]);
              });
              return box;
            };

            this.animate = function(timestamp) {
              if (!start) start = timestamp;
              now = timestamp - start;

              if (now <= time) {
                setViewBox(svg,this.getCurrent());
                this.id = requestAnimationFrame(this.animate.bind(this));
              }
              else currentAnimation = null;
            };

            this.id = requestAnimationFrame(this.animate.bind(this));
          }

          return function(svg, initialObject, finalObject, time) {
            if(currentAnimation) {
              cancelAnimationFrame(currentAnimation.id);
            }
            currentAnimation = new Animation(svg, initialObject, finalObject, time);
          };
        })();

        opts.reset = function() {
          var inivb = this.initialViewBox;
          return this.setViewBox(inivb.x, inivb.y, inivb.width, inivb.height, 0);
        };

        opts.getViewBox = function() {
          return $.extend({}, viewBox);
        };

        opts.setViewBox = function(x, y, width, height, animationTime) {
          if (animationTime == null) animationTime = this.animationTime;

          var oldBox = this.getViewBox();

          viewBox = {
            x: x != null ? x : viewBox.x,
            y: y != null ? y : viewBox.y,
            width: width ? width : viewBox.width,
            height: height ? height : viewBox.height
          };

          checkLimits(viewBox, this.limits);

          if (animationTime > 0) {
            animate(this.svg,oldBox,viewBox,animationTime);
          } else {
            setViewBox(this.svg,viewBox);
          }
          return this;
        };

        opts.panLeft = function(amount, animationTime) {
          if (amount == null) amount = this.panFactor;
          if (animationTime == null) animationTime = this.animationTime;
          return this.panRight(-amount, animationTime);
        };

        opts.panRight = function(amount, animationTime) {
          if (amount == null) amount = this.panFactor;
          if (animationTime == null) animationTime = this.animationTime;
          return this.setViewBox(viewBox.x + amount, null, null, null, animationTime);
        };

        opts.panUp = function(amount, animationTime) {
          if (amount == null) amount = this.panFactor;
          if (animationTime == null) animationTime = this.animationTime;
          return this.panDown(-amount, animationTime);
        };

        opts.panDown = function(amount, animationTime) {
          if (amount == null) amount = this.panFactor;
          if (animationTime == null) animationTime = this.animationTime;
          return this.setViewBox(null, viewBox.y + amount, null, null, animationTime);
        };

        opts.zoomIn = function(mouse , amount , animationTime) {
          if (amount == null) amount = 0 - this.zoomFactor;
          return this.zoomOut(mouse , amount, animationTime);
        };

        opts.zoomOut = function(mouse , amount, animationTime) {
          var newHeight, newWidth;
          if (amount == null) amount = this.zoomFactor;
          if (animationTime == null) animationTime = this.animationTime;

          if (amount === 0) return;
          else if (amount < 0) {
            newWidth = viewBox.width / (1 - amount);
            newHeight = viewBox.height / (1 - amount);

            //Check zoom limits
            (function (thisref) {
              var minWidth = thisref.initialViewBox.width / thisref.maxZoom;
              if (newWidth < minWidth) {
                newHeight *= minWidth / newWidth;
                newWidth = minWidth;
              }
            })(this);

            (function (thisref) {
              var minHeight = thisref.initialViewBox.height / thisref.maxZoom;
              if (newHeight < minHeight) {
                newWidth *= minHeight / newHeight;
                newHeight = minHeight;
              }
            })(this);
          } else {
            newWidth = viewBox.width * (1 + amount);
            newHeight = viewBox.height * (1 + amount);
          }

          if (mouse == null) {
            mouse = {
              x: viewBox.x + (viewBox.width - newWidth) / 2,
              y: viewBox.y + (viewBox.height - newHeight) / 2
            };
          } else {
            mouse = {
              x: mouse.x + (newWidth / viewBox.width) * (viewBox.x - mouse.x),
              y: mouse.y + (newHeight / viewBox.height) * (viewBox.y - mouse.y),
            };
          }

          this.setViewBox(mouse.x, mouse.y, newWidth, newHeight, animationTime);
          opts.onZoom(opts.initialViewBox.width / newWidth);
          return this;
        };

        opts.setCenter = function(x, y, animationTime) {
          if (animationTime == null) animationTime = this.animationTime;
          return this.setViewBox(x - viewBox.width / 2, y - viewBox.height / 2, viewBox.width, viewBox.height, animationTime);
        };

        for (var key in opts) {
          if (!opts.hasOwnProperty(key)) {
            continue;
          }
          var value = opts[key];
          if (typeof value === "function") {
            opts[key] = value.bind(opts);
          }
        }

        opts.eventDom = $(opts.eventMagnet || opts.svg);
        opts.eventDom.bind("mousewheel DOMMouseScroll MozMousePixelScroll", (function(ev) {
          var delta = parseInt(ev.originalEvent.wheelDelta || -ev.originalEvent.detail);
          if (delta === 0 || this.events.mouseWheel !== true) {
            return;
          }

          ev.preventDefault();
          ev.stopPropagation();
          var mouse = getViewBoxCoordinatesFromEvent(this.svg, ev);

          if (delta > 0) this.zoomIn(mouse);
          else this.zoomOut(mouse);
        }).bind(opts));

        opts.eventDom.dblclick((function(ev) {
          if (this.events.doubleClick !== true) return;
          ev.preventDefault();
          return this.zoomIn(getViewBoxCoordinatesFromEvent(this.svg, ev));
        }).bind(opts));

        (function() {
          var dragStarted = false;
          var scaleStarted = false;
          var preventClick = false;
          var distance = 0;

          opts.eventDom[0].addEventListener("click", function(ev) {
            if (preventClick) {
              preventClick = false;
              return ev.preventDefault();
            }
          }, true);

          opts.eventDom.bind("mousedown touchstart", (function(ev) {
            var domBody, initialViewBox, mouseMoveCallback, mouseUpCallback, oldCursor;

            if (dragStarted || scaleStarted) return;
            if (this.events.drag !== true || (ev.type === "mousedown" && ev.which !== this.events.dragMouseButton)) return;
            ev.preventDefault();

            if (ev.type === "touchstart" && isDoubleTouch(ev)) {
              scaleStarted = true;
              distance = touchDistance(ev);
            } else dragStarted = true;

            preventClick = false;
            initialViewBox = $.extend({}, viewBox);
            domBody = window.document.body;
            oldCursor = opts.eventDom.css("cursor");

            if (this.events.dragCursor != null) opts.eventDom.css("cursor", this.events.dragCursor);

            mouseMoveCallback = (function(ev2) {
              var isTouch = /touch/i.test(ev.type);
              var checkDoubleTouch = isTouch && isDoubleTouch(ev2);
              if (scaleStarted && !checkDoubleTouch) return;

              ev2.preventDefault();

              if (!scaleStarted && checkDoubleTouch) {
                scaleStarted = true;
                distance = touchDistance(ev2);
                dragStarted = false;
              }

              if (Math.sqrt(Math.pow(ev.pageX + ev2.pageX, 2) + Math.pow(ev.pageY + ev2.pageY, 2)) > 3) preventClick = true;

              if (dragStarted) {
                (function(thisref) {
                  var initialMousePosition = getViewBoxCoordinatesFromEvent(thisref.svg, ev);
                  var currentMousePosition = getViewBoxCoordinatesFromEvent(thisref.svg, ev2);
                  thisref.setViewBox(initialViewBox.x + initialMousePosition.x - currentMousePosition.x, initialViewBox.y + initialMousePosition.y - currentMousePosition.y, null, null, 0);
                })(this);
              } else if (scaleStarted) {
                (function(thisref) {
                  var newDistance = touchDistance(ev2);
                  if (newDistance == distance) {
                    return;
                  }
                  var mouse = touchCenter(thisref.svg,ev2);
                  this.zoomOut(mouse, (distance - newDistance)/distance);
                  distance = newDistance;
                })(this);
              }
            }).bind(opts);

            mouseUpCallback = (function(ev2) {
              if (ev2.type === "mouseout" && ev2.target !== ev2.currentTarget) return;
              if (ev2.type === "mouseup" && ev2.which !== this.events.dragMouseButton) return;
              ev2.preventDefault();

              domBody.removeEventListener("mousemove", mouseMoveCallback, true);
              domBody.removeEventListener("touchmove", mouseMoveCallback, true);
              domBody.removeEventListener("mouseup", mouseUpCallback, true);
              domBody.removeEventListener("touchend", mouseUpCallback, true);
              domBody.removeEventListener("touchcancel", mouseUpCallback, true);
              domBody.removeEventListener("mouseout", mouseUpCallback, true);

              if (this.events.dragCursor != null) opts.eventDom.css("cursor", oldCursor);

              dragStarted = false;
              scaleStarted = false;
              distance = 0;
            }).bind(opts);

            domBody.addEventListener("mousemove", mouseMoveCallback, true);
            domBody.addEventListener("touchmove", mouseMoveCallback, true);
            domBody.addEventListener("mouseup", mouseUpCallback, true);
            domBody.addEventListener("touchend", mouseUpCallback, true);
            domBody.addEventListener("touchcancel", mouseUpCallback, true);
            domBody.addEventListener("mouseout", mouseUpCallback, true);
          }).bind(opts));
        })();

        opts.setViewBox(viewBox.x, viewBox.y, viewBox.width, viewBox.height, 0);
        ret.push(opts);
      });

      if (ret.length === 0) {
        return null;
      }

      if (ret.length === 1) {
        return ret[0];
      } else {
        return ret;
      }
    };
  })(jQuery);

}).call(this);

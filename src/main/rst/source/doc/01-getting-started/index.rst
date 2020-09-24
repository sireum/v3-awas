.. raw:: html

   <script src="../../_static/clipboard.min.js" ></script> <script
   src="../../_static/class.js" ></script> <script
   src="../../_static/date.js" ></script> <br/> <font
   color="darkgray"> <big><big><b> Sireum Awas Documentation
   </b></big></big> </font> <br/> <font color="black">
   <big><big><big><big><b>
   1. Getting Started </b></big></big></big></big> </font>

.. highlight:: none

.. include:: ../../def.rst

.. _getting-started:

Getting Started
###############

Sireum Awas is supported in x64 macOS, Linux, and Windows operating systems,
and in Google Chrome and Mozilla Firefox browsers.

There three ways one can obtain Awas:

1. `Using osate plugin`_
2. `Using Awas pre-build Jar`_ 
3. `Using Awas source`_

.. _Using osate plugin:

Sireum Awas pluging for OSATE
******************************

.. _OSATE: http://osate.org/

OSATE_ is a customized Eclipse IDE for developing AADL models. Awas
can be installed as a plugin in OSATE.

1. Open OSATE, and navigate to Help > Install New Software ...

2. Click Add... and in the Location: field paste the following URL

   .. raw:: html

      <table width="100%" > <tr> <td>

   .. code:: bash

      https://raw.githubusercontent.com/sireum/osate-plugin-update-site/master/

   .. raw:: html

      </td> <td style="vertical-align: text-top;"> &nbsp;&nbsp;
      <button id="copyMacDevelopment"
      data-clipboard-text="https://raw.githubusercontent.com/sireum/osate-plugin-update-site/master/">
      <img height="20" src="../../_static/clippy.svg" alt="Copy to
      clipboard"/> </button> </td> </tr> </table> <script> new
      Clipboard(document.getElementById('copyMacDevelopment'));
      </script>


3. Expand the ``Sireum`` option and select ``Sireum Awas plugin`` to
   install, and then click ``Next``

   .. image:: plugin-install-1.png

4. Accept the license agreement and click ``Finish``

5. Restart OSATE when prompted

6. Skip to `Running Awas`_ section to generate an Awas visualizer for your AADL Model   

.. _Using Awas pre-build Jar:

Sireum Awas JAR
****************

Sireum Awas can be included in your OSATE plugin using the published
JAR. Use the following ``Maven`` resolver and dependency to include
Awas in your project.

1. Add the JitPack repository to your build file

   .. code:: xml

	<repositories>
	  <repository> <id>jitpack.io</id>
	    <url>https://jitpack.io</url>
	  </repository>
	</repositories>

2. Add the dependency
   
   .. code:: xml
	  
	<dependency> <groupId>com.github.sireum</groupId>
	    <artifactId>v3-awas</artifactId>
	    <version>-SNAPSHOT</version>
	</dependency>

.. _Using Awas source:

Sireum Awas source distribution
*******************************

.. _Sireum IVE: https://github.com/sireum/v3

.. _OSATE development environment: http://osate.org/setup-development.html

Awas is part of a high-assurance software development platform called
Sireum. `Sireum IVE`_ is used to develop the Awas source code and
an `OSATE development environment`_ is used to develop the Sireum Awas
OSATE plugin.


Prerequisites
=============

.. _Sireum: https://github.com/sireum/v3

.. _OSATE instructions: http://osate.org/setup-development.html


1. Install the required tools for building Sireum: bash, git, unzip,
   wget, bc

2. Follow the instructions in the Sireum_ webpage apropriate for your
   platform to install the ``Sireum IVE for development``
	     
3. Follow the `OSATE instructions`_ to setup an OSATE development
   environment. Eclipse is the underlying IDE being used by OSATE so
   the rest of plugin development instructions will refer to the IDE
   as ``Eclipse``


	     

Installing and Running Awas
===========================

There two steps in building Sireum Awas from source

1. `Building Sireum Awas Jar`_
2. `Building Sireum Awas plugin`_
   
.. _Setting up Sireum IVE:
   https://github.com/sireum/v3#setting-up-sireum-ive-for-sireum-v3-development

.. _Building Sireum Awas Jar:

Building Sireum Awas Jar
------------------------

1. Clone the following repositories
   
   - Using HTTPS:
     
     .. code:: bash
	     
	git clone --recursive https://github.com/sireum/v3.git sireum-v3 
        git clone https://github.com:sireum/air.git sireum-v3/aadl/ir
	
   - Using SSH:
     
     .. code:: bash
	     
	git clone --recursive git@github.com:sireum/v3.git sireum-v3 
        git clone git@github.com:sireum/air.git sireum-v3/aadl/ir

2. From the parent directory of ``sireum-v3`` from the previous
   step, clone the awas repository
   
   - Using HTTPS:
     
     .. code:: bash
	     
	git clone https://github.com/sireum/v3-awas.git sireum-v3/awas

   - Using SSH:
     
     .. code:: bash
	     
	git clone git@github.com:sireum/v3-awas.git sireum-v3/awas

3. Follow the `Setting up Sireum IVE`_ documentation to setup the
   Sireum development environment
   
   .. note:: Enable the ``Use sbt shell for build and import`` option

.. _Slang: https://github.com/sireum/slang

.. _ScalaJS: https://www.scala-js.org/

4. Sireum Awas is built using Scala and Slang_ with Java
   facades. Howevere, the Awas visulaizer is based on HTML5 and
   Javascript. This is accomplished by cross compiling Scala to
   Javascript using ScalaJs_

5. Building the Sireum Awas Jar
   
   - Without Scala:
     
     Execute the following shell script in your terminal

     .. code:: bash

	./sireum-v3/awas/buildAwasJar.sh
	     
     Awas Jar can be found under ``awasJar/target/scala-2.12/``

   - With Scala:
     
     Execute the following shell script in your terminal

     .. code:: bash

	./sireum-v3/awas/buildSireumJar.sh

     Rename the generated sireum.jar to awas.jar
 

.. _Building Sireum Awas Plugin:

Building Sireum Awas plugin
---------------------------

.. _Scala IDE for Eclipse: http://scala-ide.org/

1. Open the Eclipse and install the `Scala IDE for Eclipse`_ plugin

2. Clone the following repository

   .. code:: bash

      git clone git@github.com:sireum/osate-plugin.git osate-plugin

3. In Eclipse go to ``File -> Import...``

4. Expand the ``General`` folder and select ``Existing Project into
   Workspace``, then click ``Next``

5. Enter the path to the directory where you cloned the repository and
   then select all the available projects from the project listings
   and click ``Finish``

6. Switch to the plug-in development perspective: ``Window ->
   Perspective -> Open Perspective -> Other... -> Plug-in
   Development``

7. Copy the jar built in the `Building Sireum Awas Jar`_ step into the
   ``<ostate-plugin-dir>/org.sireum.aadl.osate.awas/lib/`` directory

8. Run the OSATE eclipse instance by clinking the ``Run`` button from
   the ``Toolbar`` using the ``OSATE2`` run configuration

.. _Running Awas:    

Running Awas
------------

.. _these: https://wiki.sei.cmu.edu/aadl/index.php/Editing_a_first_AADL_model

1. Open an AADL model in OSATE and build an instance model (follow
   these_ instructions to create an instance model)
   
2. Select a system implementation from the ``Outline view`` and select
   ``OSATE > Sireum Bridge > Generate Awas Visulaizer`` from the menu
   bar

   .. image:: awas_viz_save.png

3. In the popup, select a directory to save the generated Awas visualizer. 

   

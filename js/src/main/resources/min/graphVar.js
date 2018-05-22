var json = `{
              "type" : "Aadl",
              "components" : [
                {
                  "type" : "Component",
                  "identifier" : {
                    "type" : "Name",
                    "name" : ["FlightSystem_tier1_Instance"]
                  },
                  "category" : {
                    "type" : "ComponentCategory",
                    "value" : "System"
                  },
                  "classifier" : {
                    "type" : "None"
                  },
                  "features" : [
                    {
                      "type" : "Feature",
                      "identifier" : {
                        "type" : "Name",
                        "name" : ["FlightSystem_tier1_Instance", "satelliteSignal"]
                      },
                      "direction" : {
                        "type" : "Direction",
                        "value" : "In"
                      },
                      "category" : {
                        "type" : "FeatureCategory",
                        "value" : "AbstractFeature"
                      },
                      "classifier" : {
                        "type" : "None"
                      },
                      "properties" : [
                      ]
                    },
                    {
                      "type" : "Feature",
                      "identifier" : {
                        "type" : "Name",
                        "name" : ["FlightSystem_tier1_Instance", "ObservableFailure"]
                      },
                      "direction" : {
                        "type" : "Direction",
                        "value" : "Out"
                      },
                      "category" : {
                        "type" : "FeatureCategory",
                        "value" : "AbstractFeature"
                      },
                      "classifier" : {
                        "type" : "None"
                      },
                      "properties" : [
                      ]
                    },
                    {
                      "type" : "Feature",
                      "identifier" : {
                        "type" : "Name",
                        "name" : ["FlightSystem_tier1_Instance", "pilotInput"]
                      },
                      "direction" : {
                        "type" : "Direction",
                        "value" : "In"
                      },
                      "category" : {
                        "type" : "FeatureCategory",
                        "value" : "DataPort"
                      },
                      "classifier" : {
                        "type" : "None"
                      },
                      "properties" : [
                      ]
                    }
                  ],
                  "subComponents" : [
                    {
                      "type" : "Component",
                      "identifier" : {
                        "type" : "Name",
                        "name" : ["FlightSystem_tier1_Instance", "FSpowersupply"]
                      },
                      "category" : {
                        "type" : "ComponentCategory",
                        "value" : "Device"
                      },
                      "classifier" : {
                        "type" : "Some",
                        "value" : {
                          "type" : "Classifier",
                          "name" : "HardwareParts::PowerSupply"
                        }
                      },
                      "features" : [
                        {
                          "type" : "Feature",
                          "identifier" : {
                            "type" : "Name",
                            "name" : ["FlightSystem_tier1_Instance", "FSpowersupply", "power"]
                          },
                          "direction" : {
                            "type" : "Direction",
                            "value" : "Out"
                          },
                          "category" : {
                            "type" : "FeatureCategory",
                            "value" : "AbstractFeature"
                          },
                          "classifier" : {
                            "type" : "None"
                          },
                          "properties" : [
                          ]
                        }
                      ],
                      "subComponents" : [
                      ],
                      "connections" : [
                      ],
                      "connectionInstances" : [
                      ],
                      "properties" : [
                      ],
                      "flows" : [
                      ],
                      "modes" : [
                      ],
                      "annexes" : [
                        {
                          "type" : "Annex",
                          "name" : "Emv2",
                          "clause" : {
                            "type" : "Emv2Clause",
                            "libraries" : ["ErrorLibrary", "GPSErrorLibrary"],
                            "propagations" : [
                              {
                                "type" : "Emv2Propagation",
                                "direction" : {
                                  "type" : "PropagationDirection",
                                  "value" : "Out"
                                },
                                "propagationPoint" : ["FlightSystem_tier1_Instance", "FSpowersupply", "power"],
                                "errorTokens" : ["ServiceOmission"]
                              }
                            ],
                            "flows" : [
                              {
                                "type" : "Emv2Flow",
                                "identifier" : {
                                  "type" : "Name",
                                  "name" : ["FlightSystem_tier1_Instance", "FSpowersupply", "power_es"]
                                },
                                "kind" : {
                                  "type" : "FlowKind",
                                  "value" : "Source"
                                },
                                "sourcePropagation" : {
                                  "type" : "None"
                                },
                                "sinkPropagation" : {
                                  "type" : "Some",
                                  "value" : {
                                    "type" : "Emv2Propagation",
                                    "direction" : {
                                      "type" : "PropagationDirection",
                                      "value" : "Out"
                                    },
                                    "propagationPoint" : ["FlightSystem_tier1_Instance", "FSpowersupply", "power"],
                                    "errorTokens" : ["ServiceOmission"]
                                  }
                                }
                              }
                            ]
                          }
                        }
                      ]
                    },
                    {
                      "type" : "Component",
                      "identifier" : {
                        "type" : "Name",
                        "name" : ["FlightSystem_tier1_Instance", "GPS"]
                      },
                      "category" : {
                        "type" : "ComponentCategory",
                        "value" : "System"
                      },
                      "classifier" : {
                        "type" : "Some",
                        "value" : {
                          "type" : "Classifier",
                          "name" : "GPSSystem::GPS"
                        }
                      },
                      "features" : [
                        {
                          "type" : "Feature",
                          "identifier" : {
                            "type" : "Name",
                            "name" : ["FlightSystem_tier1_Instance", "GPS", "satelliteSignal"]
                          },
                          "direction" : {
                            "type" : "Direction",
                            "value" : "In"
                          },
                          "category" : {
                            "type" : "FeatureCategory",
                            "value" : "AbstractFeature"
                          },
                          "classifier" : {
                            "type" : "None"
                          },
                          "properties" : [
                          ]
                        },
                        {
                          "type" : "Feature",
                          "identifier" : {
                            "type" : "Name",
                            "name" : ["FlightSystem_tier1_Instance", "GPS", "location"]
                          },
                          "direction" : {
                            "type" : "Direction",
                            "value" : "Out"
                          },
                          "category" : {
                            "type" : "FeatureCategory",
                            "value" : "DataPort"
                          },
                          "classifier" : {
                            "type" : "None"
                          },
                          "properties" : [
                          ]
                        }
                      ],
                      "subComponents" : [
                      ],
                      "connections" : [
                      ],
                      "connectionInstances" : [
                      ],
                      "properties" : [
                      ],
                      "flows" : [
                      ],
                      "modes" : [
                      ],
                      "annexes" : [
                        {
                          "type" : "Annex",
                          "name" : "Emv2",
                          "clause" : {
                            "type" : "Emv2Clause",
                            "libraries" : ["ErrorLibrary", "GPSErrorLibrary"],
                            "propagations" : [
                              {
                                "type" : "Emv2Propagation",
                                "direction" : {
                                  "type" : "PropagationDirection",
                                  "value" : "In"
                                },
                                "propagationPoint" : ["FlightSystem_tier1_Instance", "GPS", "satelliteSignal"],
                                "errorTokens" : ["NoSignal", "LowSignal"]
                              },
                              {
                                "type" : "Emv2Propagation",
                                "direction" : {
                                  "type" : "PropagationDirection",
                                  "value" : "Out"
                                },
                                "propagationPoint" : ["FlightSystem_tier1_Instance", "GPS", "location"],
                                "errorTokens" : ["ServiceOmission", "LowPrecisionData", "IncorrectData"]
                              }
                            ],
                            "flows" : [
                              {
                                "type" : "Emv2Flow",
                                "identifier" : {
                                  "type" : "Name",
                                  "name" : ["FlightSystem_tier1_Instance", "GPS", "GPSAsErrorSource"]
                                },
                                "kind" : {
                                  "type" : "FlowKind",
                                  "value" : "Source"
                                },
                                "sourcePropagation" : {
                                  "type" : "None"
                                },
                                "sinkPropagation" : {
                                  "type" : "Some",
                                  "value" : {
                                    "type" : "Emv2Propagation",
                                    "direction" : {
                                      "type" : "PropagationDirection",
                                      "value" : "Out"
                                    },
                                    "propagationPoint" : ["FlightSystem_tier1_Instance", "GPS", "location"],
                                    "errorTokens" : ["ServiceOmission", "LowPrecisionData", "IncorrectData"]
                                  }
                                }
                              },
                              {
                                "type" : "Emv2Flow",
                                "identifier" : {
                                  "type" : "Name",
                                  "name" : ["FlightSystem_tier1_Instance", "GPS", "SatelliteError"]
                                },
                                "kind" : {
                                  "type" : "FlowKind",
                                  "value" : "Path"
                                },
                                "sourcePropagation" : {
                                  "type" : "Some",
                                  "value" : {
                                    "type" : "Emv2Propagation",
                                    "direction" : {
                                      "type" : "PropagationDirection",
                                      "value" : "In"
                                    },
                                    "propagationPoint" : ["FlightSystem_tier1_Instance", "GPS", "satelliteSignal"],
                                    "errorTokens" : ["NoSignal"]
                                  }
                                },
                                "sinkPropagation" : {
                                  "type" : "Some",
                                  "value" : {
                                    "type" : "Emv2Propagation",
                                    "direction" : {
                                      "type" : "PropagationDirection",
                                      "value" : "Out"
                                    },
                                    "propagationPoint" : ["FlightSystem_tier1_Instance", "GPS", "location"],
                                    "errorTokens" : ["ServiceOmission"]
                                  }
                                }
                              },
                              {
                                "type" : "Emv2Flow",
                                "identifier" : {
                                  "type" : "Name",
                                  "name" : ["FlightSystem_tier1_Instance", "GPS", "SatelliteError1"]
                                },
                                "kind" : {
                                  "type" : "FlowKind",
                                  "value" : "Path"
                                },
                                "sourcePropagation" : {
                                  "type" : "Some",
                                  "value" : {
                                    "type" : "Emv2Propagation",
                                    "direction" : {
                                      "type" : "PropagationDirection",
                                      "value" : "In"
                                    },
                                    "propagationPoint" : ["FlightSystem_tier1_Instance", "GPS", "satelliteSignal"],
                                    "errorTokens" : ["LowSignal"]
                                  }
                                },
                                "sinkPropagation" : {
                                  "type" : "Some",
                                  "value" : {
                                    "type" : "Emv2Propagation",
                                    "direction" : {
                                      "type" : "PropagationDirection",
                                      "value" : "Out"
                                    },
                                    "propagationPoint" : ["FlightSystem_tier1_Instance", "GPS", "location"],
                                    "errorTokens" : ["LowPrecisionData"]
                                  }
                                }
                              }
                            ]
                          }
                        }
                      ]
                    },
                    {
                      "type" : "Component",
                      "identifier" : {
                        "type" : "Name",
                        "name" : ["FlightSystem_tier1_Instance", "AutoFlightGuidance"]
                      },
                      "category" : {
                        "type" : "ComponentCategory",
                        "value" : "System"
                      },
                      "classifier" : {
                        "type" : "Some",
                        "value" : {
                          "type" : "Classifier",
                          "name" : "AutomatedFlightGuidance::AutomatedFlightGuidance"
                        }
                      },
                      "features" : [
                        {
                          "type" : "Feature",
                          "identifier" : {
                            "type" : "Name",
                            "name" : ["FlightSystem_tier1_Instance", "AutoFlightGuidance", "powersupply"]
                          },
                          "direction" : {
                            "type" : "Direction",
                            "value" : "In"
                          },
                          "category" : {
                            "type" : "FeatureCategory",
                            "value" : "AbstractFeature"
                          },
                          "classifier" : {
                            "type" : "None"
                          },
                          "properties" : [
                          ]
                        },
                        {
                          "type" : "Feature",
                          "identifier" : {
                            "type" : "Name",
                            "name" : ["FlightSystem_tier1_Instance", "AutoFlightGuidance", "position"]
                          },
                          "direction" : {
                            "type" : "Direction",
                            "value" : "In"
                          },
                          "category" : {
                            "type" : "FeatureCategory",
                            "value" : "DataPort"
                          },
                          "classifier" : {
                            "type" : "None"
                          },
                          "properties" : [
                          ]
                        },
                        {
                          "type" : "Feature",
                          "identifier" : {
                            "type" : "Name",
                            "name" : ["FlightSystem_tier1_Instance", "AutoFlightGuidance", "operatorCommand"]
                          },
                          "direction" : {
                            "type" : "Direction",
                            "value" : "In"
                          },
                          "category" : {
                            "type" : "FeatureCategory",
                            "value" : "DataPort"
                          },
                          "classifier" : {
                            "type" : "None"
                          },
                          "properties" : [
                          ]
                        },
                        {
                          "type" : "Feature",
                          "identifier" : {
                            "type" : "Name",
                            "name" : ["FlightSystem_tier1_Instance", "AutoFlightGuidance", "AFGOutput"]
                          },
                          "direction" : {
                            "type" : "Direction",
                            "value" : "Out"
                          },
                          "category" : {
                            "type" : "FeatureCategory",
                            "value" : "DataPort"
                          },
                          "classifier" : {
                            "type" : "None"
                          },
                          "properties" : [
                          ]
                        }
                      ],
                      "subComponents" : [
                      ],
                      "connections" : [
                      ],
                      "connectionInstances" : [
                      ],
                      "properties" : [
                      ],
                      "flows" : [
                      ],
                      "modes" : [
                      ],
                      "annexes" : [
                        {
                          "type" : "Annex",
                          "name" : "Emv2",
                          "clause" : {
                            "type" : "Emv2Clause",
                            "libraries" : ["AFGErrorLibrary", "GPSErrorLibrary", "ErrorLibrary"],
                            "propagations" : [
                              {
                                "type" : "Emv2Propagation",
                                "direction" : {
                                  "type" : "PropagationDirection",
                                  "value" : "In"
                                },
                                "propagationPoint" : ["FlightSystem_tier1_Instance", "AutoFlightGuidance", "position"],
                                "errorTokens" : ["ServiceOmission", "LowPrecisionData", "IncorrectData"]
                              },
                              {
                                "type" : "Emv2Propagation",
                                "direction" : {
                                  "type" : "PropagationDirection",
                                  "value" : "In"
                                },
                                "propagationPoint" : ["FlightSystem_tier1_Instance", "AutoFlightGuidance", "operatorCommand"],
                                "errorTokens" : ["NoPilotInput", "ErraticPilotInput"]
                              },
                              {
                                "type" : "Emv2Propagation",
                                "direction" : {
                                  "type" : "PropagationDirection",
                                  "value" : "In"
                                },
                                "propagationPoint" : ["FlightSystem_tier1_Instance", "AutoFlightGuidance", "powersupply"],
                                "errorTokens" : ["NoService"]
                              },
                              {
                                "type" : "Emv2Propagation",
                                "direction" : {
                                  "type" : "PropagationDirection",
                                  "value" : "Out"
                                },
                                "propagationPoint" : ["FlightSystem_tier1_Instance", "AutoFlightGuidance", "AFGOutput"],
                                "errorTokens" : ["NoService", "ErraticValue"]
                              }
                            ],
                            "flows" : [
                              {
                                "type" : "Emv2Flow",
                                "identifier" : {
                                  "type" : "Name",
                                  "name" : ["FlightSystem_tier1_Instance", "AutoFlightGuidance", "AFGsource1"]
                                },
                                "kind" : {
                                  "type" : "FlowKind",
                                  "value" : "Source"
                                },
                                "sourcePropagation" : {
                                  "type" : "None"
                                },
                                "sinkPropagation" : {
                                  "type" : "Some",
                                  "value" : {
                                    "type" : "Emv2Propagation",
                                    "direction" : {
                                      "type" : "PropagationDirection",
                                      "value" : "Out"
                                    },
                                    "propagationPoint" : ["FlightSystem_tier1_Instance", "AutoFlightGuidance", "AFGOutput"],
                                    "errorTokens" : ["ErraticValue"]
                                  }
                                }
                              },
                              {
                                "type" : "Emv2Flow",
                                "identifier" : {
                                  "type" : "Name",
                                  "name" : ["FlightSystem_tier1_Instance", "AutoFlightGuidance", "AFGsource"]
                                },
                                "kind" : {
                                  "type" : "FlowKind",
                                  "value" : "Source"
                                },
                                "sourcePropagation" : {
                                  "type" : "None"
                                },
                                "sinkPropagation" : {
                                  "type" : "Some",
                                  "value" : {
                                    "type" : "Emv2Propagation",
                                    "direction" : {
                                      "type" : "PropagationDirection",
                                      "value" : "Out"
                                    },
                                    "propagationPoint" : ["FlightSystem_tier1_Instance", "AutoFlightGuidance", "AFGOutput"],
                                    "errorTokens" : ["NoService"]
                                  }
                                }
                              },
                              {
                                "type" : "Emv2Flow",
                                "identifier" : {
                                  "type" : "Name",
                                  "name" : ["FlightSystem_tier1_Instance", "AutoFlightGuidance", "GPSPath1"]
                                },
                                "kind" : {
                                  "type" : "FlowKind",
                                  "value" : "Sink"
                                },
                                "sourcePropagation" : {
                                  "type" : "Some",
                                  "value" : {
                                    "type" : "Emv2Propagation",
                                    "direction" : {
                                      "type" : "PropagationDirection",
                                      "value" : "In"
                                    },
                                    "propagationPoint" : ["FlightSystem_tier1_Instance", "AutoFlightGuidance", "position"],
                                    "errorTokens" : ["LowPrecisionData"]
                                  }
                                },
                                "sinkPropagation" : {
                                  "type" : "None"
                                }
                              },
                              {
                                "type" : "Emv2Flow",
                                "identifier" : {
                                  "type" : "Name",
                                  "name" : ["FlightSystem_tier1_Instance", "AutoFlightGuidance", "GPSPath"]
                                },
                                "kind" : {
                                  "type" : "FlowKind",
                                  "value" : "Path"
                                },
                                "sourcePropagation" : {
                                  "type" : "Some",
                                  "value" : {
                                    "type" : "Emv2Propagation",
                                    "direction" : {
                                      "type" : "PropagationDirection",
                                      "value" : "In"
                                    },
                                    "propagationPoint" : ["FlightSystem_tier1_Instance", "AutoFlightGuidance", "position"],
                                    "errorTokens" : ["NoService"]
                                  }
                                },
                                "sinkPropagation" : {
                                  "type" : "Some",
                                  "value" : {
                                    "type" : "Emv2Propagation",
                                    "direction" : {
                                      "type" : "PropagationDirection",
                                      "value" : "Out"
                                    },
                                    "propagationPoint" : ["FlightSystem_tier1_Instance", "AutoFlightGuidance", "AFGOutput"],
                                    "errorTokens" : ["NoService"]
                                  }
                                }
                              },
                              {
                                "type" : "Emv2Flow",
                                "identifier" : {
                                  "type" : "Name",
                                  "name" : ["FlightSystem_tier1_Instance", "AutoFlightGuidance", "NoPilotPath"]
                                },
                                "kind" : {
                                  "type" : "FlowKind",
                                  "value" : "Path"
                                },
                                "sourcePropagation" : {
                                  "type" : "Some",
                                  "value" : {
                                    "type" : "Emv2Propagation",
                                    "direction" : {
                                      "type" : "PropagationDirection",
                                      "value" : "In"
                                    },
                                    "propagationPoint" : ["FlightSystem_tier1_Instance", "AutoFlightGuidance", "operatorCommand"],
                                    "errorTokens" : ["NoPilotInput"]
                                  }
                                },
                                "sinkPropagation" : {
                                  "type" : "Some",
                                  "value" : {
                                    "type" : "Emv2Propagation",
                                    "direction" : {
                                      "type" : "PropagationDirection",
                                      "value" : "Out"
                                    },
                                    "propagationPoint" : ["FlightSystem_tier1_Instance", "AutoFlightGuidance", "AFGOutput"],
                                    "errorTokens" : ["NoService"]
                                  }
                                }
                              },
                              {
                                "type" : "Emv2Flow",
                                "identifier" : {
                                  "type" : "Name",
                                  "name" : ["FlightSystem_tier1_Instance", "AutoFlightGuidance", "ErraticPilotPath"]
                                },
                                "kind" : {
                                  "type" : "FlowKind",
                                  "value" : "Path"
                                },
                                "sourcePropagation" : {
                                  "type" : "Some",
                                  "value" : {
                                    "type" : "Emv2Propagation",
                                    "direction" : {
                                      "type" : "PropagationDirection",
                                      "value" : "In"
                                    },
                                    "propagationPoint" : ["FlightSystem_tier1_Instance", "AutoFlightGuidance", "operatorCommand"],
                                    "errorTokens" : ["ErraticPilotInput"]
                                  }
                                },
                                "sinkPropagation" : {
                                  "type" : "Some",
                                  "value" : {
                                    "type" : "Emv2Propagation",
                                    "direction" : {
                                      "type" : "PropagationDirection",
                                      "value" : "Out"
                                    },
                                    "propagationPoint" : ["FlightSystem_tier1_Instance", "AutoFlightGuidance", "AFGOutput"],
                                    "errorTokens" : ["ErraticValue"]
                                  }
                                }
                              },
                              {
                                "type" : "Emv2Flow",
                                "identifier" : {
                                  "type" : "Name",
                                  "name" : ["FlightSystem_tier1_Instance", "AutoFlightGuidance", "epPower"]
                                },
                                "kind" : {
                                  "type" : "FlowKind",
                                  "value" : "Path"
                                },
                                "sourcePropagation" : {
                                  "type" : "Some",
                                  "value" : {
                                    "type" : "Emv2Propagation",
                                    "direction" : {
                                      "type" : "PropagationDirection",
                                      "value" : "In"
                                    },
                                    "propagationPoint" : ["FlightSystem_tier1_Instance", "AutoFlightGuidance", "powersupply"],
                                    "errorTokens" : ["NoService"]
                                  }
                                },
                                "sinkPropagation" : {
                                  "type" : "Some",
                                  "value" : {
                                    "type" : "Emv2Propagation",
                                    "direction" : {
                                      "type" : "PropagationDirection",
                                      "value" : "Out"
                                    },
                                    "propagationPoint" : ["FlightSystem_tier1_Instance", "AutoFlightGuidance", "AFGOutput"],
                                    "errorTokens" : ["NoService"]
                                  }
                                }
                              }
                            ]
                          }
                        }
                      ]
                    },
                    {
                      "type" : "Component",
                      "identifier" : {
                        "type" : "Name",
                        "name" : ["FlightSystem_tier1_Instance", "FlightControl"]
                      },
                      "category" : {
                        "type" : "ComponentCategory",
                        "value" : "System"
                      },
                      "classifier" : {
                        "type" : "Some",
                        "value" : {
                          "type" : "Classifier",
                          "name" : "FlightControl::FlightControl"
                        }
                      },
                      "features" : [
                        {
                          "type" : "Feature",
                          "identifier" : {
                            "type" : "Name",
                            "name" : ["FlightSystem_tier1_Instance", "FlightControl", "powersupply"]
                          },
                          "direction" : {
                            "type" : "Direction",
                            "value" : "In"
                          },
                          "category" : {
                            "type" : "FeatureCategory",
                            "value" : "AbstractFeature"
                          },
                          "classifier" : {
                            "type" : "None"
                          },
                          "properties" : [
                          ]
                        },
                        {
                          "type" : "Feature",
                          "identifier" : {
                            "type" : "Name",
                            "name" : ["FlightSystem_tier1_Instance", "FlightControl", "guidanceCommands"]
                          },
                          "direction" : {
                            "type" : "Direction",
                            "value" : "In"
                          },
                          "category" : {
                            "type" : "FeatureCategory",
                            "value" : "DataPort"
                          },
                          "classifier" : {
                            "type" : "None"
                          },
                          "properties" : [
                          ]
                        },
                        {
                          "type" : "Feature",
                          "identifier" : {
                            "type" : "Name",
                            "name" : ["FlightSystem_tier1_Instance", "FlightControl", "flightSurfaceControl"]
                          },
                          "direction" : {
                            "type" : "Direction",
                            "value" : "Out"
                          },
                          "category" : {
                            "type" : "FeatureCategory",
                            "value" : "DataPort"
                          },
                          "classifier" : {
                            "type" : "None"
                          },
                          "properties" : [
                          ]
                        }
                      ],
                      "subComponents" : [
                      ],
                      "connections" : [
                      ],
                      "connectionInstances" : [
                      ],
                      "properties" : [
                      ],
                      "flows" : [
                      ],
                      "modes" : [
                      ],
                      "annexes" : [
                        {
                          "type" : "Annex",
                          "name" : "Emv2",
                          "clause" : {
                            "type" : "Emv2Clause",
                            "libraries" : ["AFGErrorLibrary"],
                            "propagations" : [
                              {
                                "type" : "Emv2Propagation",
                                "direction" : {
                                  "type" : "PropagationDirection",
                                  "value" : "In"
                                },
                                "propagationPoint" : ["FlightSystem_tier1_Instance", "FlightControl", "guidanceCommands"],
                                "errorTokens" : ["NoService", "ErraticValue"]
                              },
                              {
                                "type" : "Emv2Propagation",
                                "direction" : {
                                  "type" : "PropagationDirection",
                                  "value" : "In"
                                },
                                "propagationPoint" : ["FlightSystem_tier1_Instance", "FlightControl", "powersupply"],
                                "errorTokens" : ["NoService"]
                              },
                              {
                                "type" : "Emv2Propagation",
                                "direction" : {
                                  "type" : "PropagationDirection",
                                  "value" : "Out"
                                },
                                "propagationPoint" : ["FlightSystem_tier1_Instance", "FlightControl", "flightSurfaceControl"],
                                "errorTokens" : ["NoService", "ErraticBehavior"]
                              }
                            ],
                            "flows" : [
                              {
                                "type" : "Emv2Flow",
                                "identifier" : {
                                  "type" : "Name",
                                  "name" : ["FlightSystem_tier1_Instance", "FlightControl", "fces"]
                                },
                                "kind" : {
                                  "type" : "FlowKind",
                                  "value" : "Source"
                                },
                                "sourcePropagation" : {
                                  "type" : "None"
                                },
                                "sinkPropagation" : {
                                  "type" : "Some",
                                  "value" : {
                                    "type" : "Emv2Propagation",
                                    "direction" : {
                                      "type" : "PropagationDirection",
                                      "value" : "Out"
                                    },
                                    "propagationPoint" : ["FlightSystem_tier1_Instance", "FlightControl", "flightSurfaceControl"],
                                    "errorTokens" : ["NoService"]
                                  }
                                }
                              },
                              {
                                "type" : "Emv2Flow",
                                "identifier" : {
                                  "type" : "Name",
                                  "name" : ["FlightSystem_tier1_Instance", "FlightControl", "fgtofcNo"]
                                },
                                "kind" : {
                                  "type" : "FlowKind",
                                  "value" : "Path"
                                },
                                "sourcePropagation" : {
                                  "type" : "Some",
                                  "value" : {
                                    "type" : "Emv2Propagation",
                                    "direction" : {
                                      "type" : "PropagationDirection",
                                      "value" : "In"
                                    },
                                    "propagationPoint" : ["FlightSystem_tier1_Instance", "FlightControl", "guidanceCommands"],
                                    "errorTokens" : ["NoService"]
                                  }
                                },
                                "sinkPropagation" : {
                                  "type" : "Some",
                                  "value" : {
                                    "type" : "Emv2Propagation",
                                    "direction" : {
                                      "type" : "PropagationDirection",
                                      "value" : "Out"
                                    },
                                    "propagationPoint" : ["FlightSystem_tier1_Instance", "FlightControl", "flightSurfaceControl"],
                                    "errorTokens" : ["NoService"]
                                  }
                                }
                              },
                              {
                                "type" : "Emv2Flow",
                                "identifier" : {
                                  "type" : "Name",
                                  "name" : ["FlightSystem_tier1_Instance", "FlightControl", "fgtofcErratic"]
                                },
                                "kind" : {
                                  "type" : "FlowKind",
                                  "value" : "Path"
                                },
                                "sourcePropagation" : {
                                  "type" : "Some",
                                  "value" : {
                                    "type" : "Emv2Propagation",
                                    "direction" : {
                                      "type" : "PropagationDirection",
                                      "value" : "In"
                                    },
                                    "propagationPoint" : ["FlightSystem_tier1_Instance", "FlightControl", "guidanceCommands"],
                                    "errorTokens" : ["ErraticValue"]
                                  }
                                },
                                "sinkPropagation" : {
                                  "type" : "Some",
                                  "value" : {
                                    "type" : "Emv2Propagation",
                                    "direction" : {
                                      "type" : "PropagationDirection",
                                      "value" : "Out"
                                    },
                                    "propagationPoint" : ["FlightSystem_tier1_Instance", "FlightControl", "flightSurfaceControl"],
                                    "errorTokens" : ["ErraticBehavior"]
                                  }
                                }
                              },
                              {
                                "type" : "Emv2Flow",
                                "identifier" : {
                                  "type" : "Name",
                                  "name" : ["FlightSystem_tier1_Instance", "FlightControl", "epPower"]
                                },
                                "kind" : {
                                  "type" : "FlowKind",
                                  "value" : "Path"
                                },
                                "sourcePropagation" : {
                                  "type" : "Some",
                                  "value" : {
                                    "type" : "Emv2Propagation",
                                    "direction" : {
                                      "type" : "PropagationDirection",
                                      "value" : "In"
                                    },
                                    "propagationPoint" : ["FlightSystem_tier1_Instance", "FlightControl", "powersupply"],
                                    "errorTokens" : ["NoService"]
                                  }
                                },
                                "sinkPropagation" : {
                                  "type" : "Some",
                                  "value" : {
                                    "type" : "Emv2Propagation",
                                    "direction" : {
                                      "type" : "PropagationDirection",
                                      "value" : "Out"
                                    },
                                    "propagationPoint" : ["FlightSystem_tier1_Instance", "FlightControl", "flightSurfaceControl"],
                                    "errorTokens" : ["NoService"]
                                  }
                                }
                              }
                            ]
                          }
                        }
                      ]
                    }
                  ],
                  "connections" : [
                    {
                      "type" : "Connection",
                      "name" : {
                        "type" : "Name",
                        "name" : ["FlightSystem_tier1_Instance", "satellite"]
                      },
                      "src" : {
                        "type" : "EndPoint",
                        "component" : {
                          "type" : "Name",
                          "name" : ["FlightSystem_tier1_Instance"]
                        },
                        "feature" : {
                          "type" : "Name",
                          "name" : ["FlightSystem_tier1_Instance", "satelliteSignal"]
                        },
                        "direction" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Direction",
                            "value" : "In"
                          }
                        }
                      },
                      "dst" : {
                        "type" : "EndPoint",
                        "component" : {
                          "type" : "Name",
                          "name" : ["FlightSystem_tier1_Instance", "GPS"]
                        },
                        "feature" : {
                          "type" : "Name",
                          "name" : ["FlightSystem_tier1_Instance", "GPS", "satelliteSignal"]
                        },
                        "direction" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Direction",
                            "value" : "In"
                          }
                        }
                      },
                      "isBiDirectional" : false,
                      "connectionInstances" : [
                        {
                          "type" : "Name",
                          "name" : ["FlightSystem_tier1_Instance", "satelliteSignal -> GPS", "satelliteSignal"]
                        }
                      ],
                      "properties" : [
                      ]
                    },
                    {
                      "type" : "Connection",
                      "name" : {
                        "type" : "Name",
                        "name" : ["FlightSystem_tier1_Instance", "PilottoAfg"]
                      },
                      "src" : {
                        "type" : "EndPoint",
                        "component" : {
                          "type" : "Name",
                          "name" : ["FlightSystem_tier1_Instance"]
                        },
                        "feature" : {
                          "type" : "Name",
                          "name" : ["FlightSystem_tier1_Instance", "pilotInput"]
                        },
                        "direction" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Direction",
                            "value" : "In"
                          }
                        }
                      },
                      "dst" : {
                        "type" : "EndPoint",
                        "component" : {
                          "type" : "Name",
                          "name" : ["FlightSystem_tier1_Instance", "AutoFlightGuidance"]
                        },
                        "feature" : {
                          "type" : "Name",
                          "name" : ["FlightSystem_tier1_Instance", "AutoFlightGuidance", "operatorCommand"]
                        },
                        "direction" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Direction",
                            "value" : "In"
                          }
                        }
                      },
                      "isBiDirectional" : false,
                      "connectionInstances" : [
                        {
                          "type" : "Name",
                          "name" : ["FlightSystem_tier1_Instance", "pilotInput -> AutoFlightGuidance", "operatorCommand"]
                        }
                      ],
                      "properties" : [
                      ]
                    },
                    {
                      "type" : "Connection",
                      "name" : {
                        "type" : "Name",
                        "name" : ["FlightSystem_tier1_Instance", "power1"]
                      },
                      "src" : {
                        "type" : "EndPoint",
                        "component" : {
                          "type" : "Name",
                          "name" : ["FlightSystem_tier1_Instance", "FSpowersupply"]
                        },
                        "feature" : {
                          "type" : "Name",
                          "name" : ["FlightSystem_tier1_Instance", "FSpowersupply", "power"]
                        },
                        "direction" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Direction",
                            "value" : "Out"
                          }
                        }
                      },
                      "dst" : {
                        "type" : "EndPoint",
                        "component" : {
                          "type" : "Name",
                          "name" : ["FlightSystem_tier1_Instance", "AutoFlightGuidance"]
                        },
                        "feature" : {
                          "type" : "Name",
                          "name" : ["FlightSystem_tier1_Instance", "AutoFlightGuidance", "powersupply"]
                        },
                        "direction" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Direction",
                            "value" : "In"
                          }
                        }
                      },
                      "isBiDirectional" : false,
                      "connectionInstances" : [
                        {
                          "type" : "Name",
                          "name" : ["FlightSystem_tier1_Instance", "FSpowersupply", "power -> AutoFlightGuidance", "powersupply"]
                        }
                      ],
                      "properties" : [
                      ]
                    },
                    {
                      "type" : "Connection",
                      "name" : {
                        "type" : "Name",
                        "name" : ["FlightSystem_tier1_Instance", "power2"]
                      },
                      "src" : {
                        "type" : "EndPoint",
                        "component" : {
                          "type" : "Name",
                          "name" : ["FlightSystem_tier1_Instance", "FSpowersupply"]
                        },
                        "feature" : {
                          "type" : "Name",
                          "name" : ["FlightSystem_tier1_Instance", "FSpowersupply", "power"]
                        },
                        "direction" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Direction",
                            "value" : "Out"
                          }
                        }
                      },
                      "dst" : {
                        "type" : "EndPoint",
                        "component" : {
                          "type" : "Name",
                          "name" : ["FlightSystem_tier1_Instance", "FlightControl"]
                        },
                        "feature" : {
                          "type" : "Name",
                          "name" : ["FlightSystem_tier1_Instance", "FlightControl", "powersupply"]
                        },
                        "direction" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Direction",
                            "value" : "In"
                          }
                        }
                      },
                      "isBiDirectional" : false,
                      "connectionInstances" : [
                        {
                          "type" : "Name",
                          "name" : ["FlightSystem_tier1_Instance", "FSpowersupply", "power -> FlightControl", "powersupply"]
                        }
                      ],
                      "properties" : [
                      ]
                    },
                    {
                      "type" : "Connection",
                      "name" : {
                        "type" : "Name",
                        "name" : ["FlightSystem_tier1_Instance", "gpstoafg"]
                      },
                      "src" : {
                        "type" : "EndPoint",
                        "component" : {
                          "type" : "Name",
                          "name" : ["FlightSystem_tier1_Instance", "GPS"]
                        },
                        "feature" : {
                          "type" : "Name",
                          "name" : ["FlightSystem_tier1_Instance", "GPS", "location"]
                        },
                        "direction" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Direction",
                            "value" : "Out"
                          }
                        }
                      },
                      "dst" : {
                        "type" : "EndPoint",
                        "component" : {
                          "type" : "Name",
                          "name" : ["FlightSystem_tier1_Instance", "AutoFlightGuidance"]
                        },
                        "feature" : {
                          "type" : "Name",
                          "name" : ["FlightSystem_tier1_Instance", "AutoFlightGuidance", "position"]
                        },
                        "direction" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Direction",
                            "value" : "In"
                          }
                        }
                      },
                      "isBiDirectional" : false,
                      "connectionInstances" : [
                        {
                          "type" : "Name",
                          "name" : ["FlightSystem_tier1_Instance", "GPS", "location -> AutoFlightGuidance", "position"]
                        }
                      ],
                      "properties" : [
                      ]
                    },
                    {
                      "type" : "Connection",
                      "name" : {
                        "type" : "Name",
                        "name" : ["FlightSystem_tier1_Instance", "afgtofc"]
                      },
                      "src" : {
                        "type" : "EndPoint",
                        "component" : {
                          "type" : "Name",
                          "name" : ["FlightSystem_tier1_Instance", "AutoFlightGuidance"]
                        },
                        "feature" : {
                          "type" : "Name",
                          "name" : ["FlightSystem_tier1_Instance", "AutoFlightGuidance", "AFGOutput"]
                        },
                        "direction" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Direction",
                            "value" : "Out"
                          }
                        }
                      },
                      "dst" : {
                        "type" : "EndPoint",
                        "component" : {
                          "type" : "Name",
                          "name" : ["FlightSystem_tier1_Instance", "FlightControl"]
                        },
                        "feature" : {
                          "type" : "Name",
                          "name" : ["FlightSystem_tier1_Instance", "FlightControl", "guidanceCommands"]
                        },
                        "direction" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Direction",
                            "value" : "In"
                          }
                        }
                      },
                      "isBiDirectional" : false,
                      "connectionInstances" : [
                        {
                          "type" : "Name",
                          "name" : ["FlightSystem_tier1_Instance", "AutoFlightGuidance", "AFGOutput -> FlightControl", "guidanceCommands"]
                        }
                      ],
                      "properties" : [
                      ]
                    },
                    {
                      "type" : "Connection",
                      "name" : {
                        "type" : "Name",
                        "name" : ["FlightSystem_tier1_Instance", "FlightControlEffect"]
                      },
                      "src" : {
                        "type" : "EndPoint",
                        "component" : {
                          "type" : "Name",
                          "name" : ["FlightSystem_tier1_Instance", "FlightControl"]
                        },
                        "feature" : {
                          "type" : "Name",
                          "name" : ["FlightSystem_tier1_Instance", "FlightControl", "flightSurfaceControl"]
                        },
                        "direction" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Direction",
                            "value" : "Out"
                          }
                        }
                      },
                      "dst" : {
                        "type" : "EndPoint",
                        "component" : {
                          "type" : "Name",
                          "name" : ["FlightSystem_tier1_Instance"]
                        },
                        "feature" : {
                          "type" : "Name",
                          "name" : ["FlightSystem_tier1_Instance", "ObservableFailure"]
                        },
                        "direction" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Direction",
                            "value" : "Out"
                          }
                        }
                      },
                      "isBiDirectional" : false,
                      "connectionInstances" : [
                        {
                          "type" : "Name",
                          "name" : ["FlightSystem_tier1_Instance", "FlightControl", "flightSurfaceControl -> ObservableFailure"]
                        }
                      ],
                      "properties" : [
                      ]
                    }
                  ],
                  "connectionInstances" : [
                    {
                      "type" : "ConnectionInstance",
                      "name" : {
                        "type" : "Name",
                        "name" : ["FlightSystem_tier1_Instance", "satelliteSignal -> GPS.satelliteSignal"]
                      },
                      "src" : {
                        "type" : "EndPoint",
                        "component" : {
                          "type" : "Name",
                          "name" : ["FlightSystem_tier1_Instance"]
                        },
                        "feature" : {
                          "type" : "Name",
                          "name" : ["FlightSystem_tier1_Instance", "satelliteSignal"]
                        },
                        "direction" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Direction",
                            "value" : "In"
                          }
                        }
                      },
                      "dst" : {
                        "type" : "EndPoint",
                        "component" : {
                          "type" : "Name",
                          "name" : ["FlightSystem_tier1_Instance", "GPS"]
                        },
                        "feature" : {
                          "type" : "Name",
                          "name" : ["FlightSystem_tier1_Instance", "GPS", "satelliteSignal"]
                        },
                        "direction" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Direction",
                            "value" : "In"
                          }
                        }
                      },
                      "kind" : {
                        "type" : "ConnectionKind",
                        "value" : "Feature"
                      },
                      "connectionRefs" : [
                        {
                          "type" : "ConnectionReference",
                          "name" : {
                            "type" : "Name",
                            "name" : ["FlightSystem_tier1_Instance", "satellite"]
                          },
                          "context" : {
                            "type" : "Name",
                            "name" : ["FlightSystem_tier1_Instance"]
                          },
                          "isParent" : true
                        }
                      ],
                      "properties" : [
                      ]
                    },
                    {
                      "type" : "ConnectionInstance",
                      "name" : {
                        "type" : "Name",
                        "name" : ["FlightSystem_tier1_Instance", "pilotInput -> AutoFlightGuidance.operatorCommand"]
                      },
                      "src" : {
                        "type" : "EndPoint",
                        "component" : {
                          "type" : "Name",
                          "name" : ["FlightSystem_tier1_Instance"]
                        },
                        "feature" : {
                          "type" : "Name",
                          "name" : ["FlightSystem_tier1_Instance", "pilotInput"]
                        },
                        "direction" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Direction",
                            "value" : "In"
                          }
                        }
                      },
                      "dst" : {
                        "type" : "EndPoint",
                        "component" : {
                          "type" : "Name",
                          "name" : ["FlightSystem_tier1_Instance", "AutoFlightGuidance"]
                        },
                        "feature" : {
                          "type" : "Name",
                          "name" : ["FlightSystem_tier1_Instance", "AutoFlightGuidance", "operatorCommand"]
                        },
                        "direction" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Direction",
                            "value" : "In"
                          }
                        }
                      },
                      "kind" : {
                        "type" : "ConnectionKind",
                        "value" : "Port"
                      },
                      "connectionRefs" : [
                        {
                          "type" : "ConnectionReference",
                          "name" : {
                            "type" : "Name",
                            "name" : ["FlightSystem_tier1_Instance", "PilottoAfg"]
                          },
                          "context" : {
                            "type" : "Name",
                            "name" : ["FlightSystem_tier1_Instance"]
                          },
                          "isParent" : true
                        }
                      ],
                      "properties" : [
                      ]
                    },
                    {
                      "type" : "ConnectionInstance",
                      "name" : {
                        "type" : "Name",
                        "name" : ["FlightSystem_tier1_Instance", "FSpowersupply.power -> AutoFlightGuidance.powersupply"]
                      },
                      "src" : {
                        "type" : "EndPoint",
                        "component" : {
                          "type" : "Name",
                          "name" : ["FlightSystem_tier1_Instance", "FSpowersupply"]
                        },
                        "feature" : {
                          "type" : "Name",
                          "name" : ["FlightSystem_tier1_Instance", "FSpowersupply", "power"]
                        },
                        "direction" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Direction",
                            "value" : "Out"
                          }
                        }
                      },
                      "dst" : {
                        "type" : "EndPoint",
                        "component" : {
                          "type" : "Name",
                          "name" : ["FlightSystem_tier1_Instance", "AutoFlightGuidance"]
                        },
                        "feature" : {
                          "type" : "Name",
                          "name" : ["FlightSystem_tier1_Instance", "AutoFlightGuidance", "powersupply"]
                        },
                        "direction" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Direction",
                            "value" : "In"
                          }
                        }
                      },
                      "kind" : {
                        "type" : "ConnectionKind",
                        "value" : "Feature"
                      },
                      "connectionRefs" : [
                        {
                          "type" : "ConnectionReference",
                          "name" : {
                            "type" : "Name",
                            "name" : ["FlightSystem_tier1_Instance", "power1"]
                          },
                          "context" : {
                            "type" : "Name",
                            "name" : ["FlightSystem_tier1_Instance"]
                          },
                          "isParent" : true
                        }
                      ],
                      "properties" : [
                      ]
                    },
                    {
                      "type" : "ConnectionInstance",
                      "name" : {
                        "type" : "Name",
                        "name" : ["FlightSystem_tier1_Instance", "FSpowersupply.power -> FlightControl.powersupply"]
                      },
                      "src" : {
                        "type" : "EndPoint",
                        "component" : {
                          "type" : "Name",
                          "name" : ["FlightSystem_tier1_Instance", "FSpowersupply"]
                        },
                        "feature" : {
                          "type" : "Name",
                          "name" : ["FlightSystem_tier1_Instance", "FSpowersupply", "power"]
                        },
                        "direction" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Direction",
                            "value" : "Out"
                          }
                        }
                      },
                      "dst" : {
                        "type" : "EndPoint",
                        "component" : {
                          "type" : "Name",
                          "name" : ["FlightSystem_tier1_Instance", "FlightControl"]
                        },
                        "feature" : {
                          "type" : "Name",
                          "name" : ["FlightSystem_tier1_Instance", "FlightControl", "powersupply"]
                        },
                        "direction" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Direction",
                            "value" : "In"
                          }
                        }
                      },
                      "kind" : {
                        "type" : "ConnectionKind",
                        "value" : "Feature"
                      },
                      "connectionRefs" : [
                        {
                          "type" : "ConnectionReference",
                          "name" : {
                            "type" : "Name",
                            "name" : ["FlightSystem_tier1_Instance", "power2"]
                          },
                          "context" : {
                            "type" : "Name",
                            "name" : ["FlightSystem_tier1_Instance"]
                          },
                          "isParent" : true
                        }
                      ],
                      "properties" : [
                      ]
                    },
                    {
                      "type" : "ConnectionInstance",
                      "name" : {
                        "type" : "Name",
                        "name" : ["FlightSystem_tier1_Instance", "GPS.location -> AutoFlightGuidance.position"]
                      },
                      "src" : {
                        "type" : "EndPoint",
                        "component" : {
                          "type" : "Name",
                          "name" : ["FlightSystem_tier1_Instance", "GPS"]
                        },
                        "feature" : {
                          "type" : "Name",
                          "name" : ["FlightSystem_tier1_Instance", "GPS", "location"]
                        },
                        "direction" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Direction",
                            "value" : "Out"
                          }
                        }
                      },
                      "dst" : {
                        "type" : "EndPoint",
                        "component" : {
                          "type" : "Name",
                          "name" : ["FlightSystem_tier1_Instance", "AutoFlightGuidance"]
                        },
                        "feature" : {
                          "type" : "Name",
                          "name" : ["FlightSystem_tier1_Instance", "AutoFlightGuidance", "position"]
                        },
                        "direction" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Direction",
                            "value" : "In"
                          }
                        }
                      },
                      "kind" : {
                        "type" : "ConnectionKind",
                        "value" : "Port"
                      },
                      "connectionRefs" : [
                        {
                          "type" : "ConnectionReference",
                          "name" : {
                            "type" : "Name",
                            "name" : ["FlightSystem_tier1_Instance", "gpstoafg"]
                          },
                          "context" : {
                            "type" : "Name",
                            "name" : ["FlightSystem_tier1_Instance"]
                          },
                          "isParent" : true
                        }
                      ],
                      "properties" : [
                      ]
                    },
                    {
                      "type" : "ConnectionInstance",
                      "name" : {
                        "type" : "Name",
                        "name" : ["FlightSystem_tier1_Instance", "AutoFlightGuidance.AFGOutput -> FlightControl.guidanceCommands"]
                      },
                      "src" : {
                        "type" : "EndPoint",
                        "component" : {
                          "type" : "Name",
                          "name" : ["FlightSystem_tier1_Instance", "AutoFlightGuidance"]
                        },
                        "feature" : {
                          "type" : "Name",
                          "name" : ["FlightSystem_tier1_Instance", "AutoFlightGuidance", "AFGOutput"]
                        },
                        "direction" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Direction",
                            "value" : "Out"
                          }
                        }
                      },
                      "dst" : {
                        "type" : "EndPoint",
                        "component" : {
                          "type" : "Name",
                          "name" : ["FlightSystem_tier1_Instance", "FlightControl"]
                        },
                        "feature" : {
                          "type" : "Name",
                          "name" : ["FlightSystem_tier1_Instance", "FlightControl", "guidanceCommands"]
                        },
                        "direction" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Direction",
                            "value" : "In"
                          }
                        }
                      },
                      "kind" : {
                        "type" : "ConnectionKind",
                        "value" : "Port"
                      },
                      "connectionRefs" : [
                        {
                          "type" : "ConnectionReference",
                          "name" : {
                            "type" : "Name",
                            "name" : ["FlightSystem_tier1_Instance", "afgtofc"]
                          },
                          "context" : {
                            "type" : "Name",
                            "name" : ["FlightSystem_tier1_Instance"]
                          },
                          "isParent" : true
                        }
                      ],
                      "properties" : [
                      ]
                    },
                    {
                      "type" : "ConnectionInstance",
                      "name" : {
                        "type" : "Name",
                        "name" : ["FlightSystem_tier1_Instance", "FlightControl.flightSurfaceControl -> ObservableFailure"]
                      },
                      "src" : {
                        "type" : "EndPoint",
                        "component" : {
                          "type" : "Name",
                          "name" : ["FlightSystem_tier1_Instance", "FlightControl"]
                        },
                        "feature" : {
                          "type" : "Name",
                          "name" : ["FlightSystem_tier1_Instance", "FlightControl", "flightSurfaceControl"]
                        },
                        "direction" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Direction",
                            "value" : "Out"
                          }
                        }
                      },
                      "dst" : {
                        "type" : "EndPoint",
                        "component" : {
                          "type" : "Name",
                          "name" : ["FlightSystem_tier1_Instance"]
                        },
                        "feature" : {
                          "type" : "Name",
                          "name" : ["FlightSystem_tier1_Instance", "ObservableFailure"]
                        },
                        "direction" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Direction",
                            "value" : "Out"
                          }
                        }
                      },
                      "kind" : {
                        "type" : "ConnectionKind",
                        "value" : "Feature"
                      },
                      "connectionRefs" : [
                        {
                          "type" : "ConnectionReference",
                          "name" : {
                            "type" : "Name",
                            "name" : ["FlightSystem_tier1_Instance", "FlightControlEffect"]
                          },
                          "context" : {
                            "type" : "Name",
                            "name" : ["FlightSystem_tier1_Instance"]
                          },
                          "isParent" : true
                        }
                      ],
                      "properties" : [
                      ]
                    }
                  ],
                  "properties" : [
                  ],
                  "flows" : [
                  ],
                  "modes" : [
                  ],
                  "annexes" : [
                    {
                      "type" : "Annex",
                      "name" : "Emv2",
                      "clause" : {
                        "type" : "Emv2Clause",
                        "libraries" : ["ErrorLibrary", "GPSErrorLibrary", "AFGErrorLibrary"],
                        "propagations" : [
                          {
                            "type" : "Emv2Propagation",
                            "direction" : {
                              "type" : "PropagationDirection",
                              "value" : "In"
                            },
                            "propagationPoint" : ["FlightSystem_tier1_Instance", "satelliteSignal"],
                            "errorTokens" : ["NoSignal", "LowSignal"]
                          },
                          {
                            "type" : "Emv2Propagation",
                            "direction" : {
                              "type" : "PropagationDirection",
                              "value" : "In"
                            },
                            "propagationPoint" : ["FlightSystem_tier1_Instance", "pilotInput"],
                            "errorTokens" : ["NoPilotInput", "ErraticPilotInput"]
                          },
                          {
                            "type" : "Emv2Propagation",
                            "direction" : {
                              "type" : "PropagationDirection",
                              "value" : "Out"
                            },
                            "propagationPoint" : ["FlightSystem_tier1_Instance", "ObservableFailure"],
                            "errorTokens" : ["ServiceOmission", "ErraticBehavior"]
                          }
                        ],
                        "flows" : [
                          {
                            "type" : "Emv2Flow",
                            "identifier" : {
                              "type" : "Name",
                              "name" : ["FlightSystem_tier1_Instance", "NoFlightSystem"]
                            },
                            "kind" : {
                              "type" : "FlowKind",
                              "value" : "Source"
                            },
                            "sourcePropagation" : {
                              "type" : "None"
                            },
                            "sinkPropagation" : {
                              "type" : "Some",
                              "value" : {
                                "type" : "Emv2Propagation",
                                "direction" : {
                                  "type" : "PropagationDirection",
                                  "value" : "Out"
                                },
                                "propagationPoint" : ["FlightSystem_tier1_Instance", "ObservableFailure"],
                                "errorTokens" : ["ServiceOmission"]
                              }
                            }
                          },
                          {
                            "type" : "Emv2Flow",
                            "identifier" : {
                              "type" : "Name",
                              "name" : ["FlightSystem_tier1_Instance", "ErraticFlightSystem"]
                            },
                            "kind" : {
                              "type" : "FlowKind",
                              "value" : "Source"
                            },
                            "sourcePropagation" : {
                              "type" : "None"
                            },
                            "sinkPropagation" : {
                              "type" : "Some",
                              "value" : {
                                "type" : "Emv2Propagation",
                                "direction" : {
                                  "type" : "PropagationDirection",
                                  "value" : "Out"
                                },
                                "propagationPoint" : ["FlightSystem_tier1_Instance", "ObservableFailure"],
                                "errorTokens" : ["ErraticBehavior"]
                              }
                            }
                          },
                          {
                            "type" : "Emv2Flow",
                            "identifier" : {
                              "type" : "Name",
                              "name" : ["FlightSystem_tier1_Instance", "ErraticPilot"]
                            },
                            "kind" : {
                              "type" : "FlowKind",
                              "value" : "Path"
                            },
                            "sourcePropagation" : {
                              "type" : "Some",
                              "value" : {
                                "type" : "Emv2Propagation",
                                "direction" : {
                                  "type" : "PropagationDirection",
                                  "value" : "In"
                                },
                                "propagationPoint" : ["FlightSystem_tier1_Instance", "pilotInput"],
                                "errorTokens" : ["ErraticPilotInput"]
                              }
                            },
                            "sinkPropagation" : {
                              "type" : "Some",
                              "value" : {
                                "type" : "Emv2Propagation",
                                "direction" : {
                                  "type" : "PropagationDirection",
                                  "value" : "Out"
                                },
                                "propagationPoint" : ["FlightSystem_tier1_Instance", "ObservableFailure"],
                                "errorTokens" : ["ErraticBehavior"]
                              }
                            }
                          },
                          {
                            "type" : "Emv2Flow",
                            "identifier" : {
                              "type" : "Name",
                              "name" : ["FlightSystem_tier1_Instance", "NoPilot"]
                            },
                            "kind" : {
                              "type" : "FlowKind",
                              "value" : "Path"
                            },
                            "sourcePropagation" : {
                              "type" : "Some",
                              "value" : {
                                "type" : "Emv2Propagation",
                                "direction" : {
                                  "type" : "PropagationDirection",
                                  "value" : "In"
                                },
                                "propagationPoint" : ["FlightSystem_tier1_Instance", "pilotInput"],
                                "errorTokens" : ["NoPilotInput"]
                              }
                            },
                            "sinkPropagation" : {
                              "type" : "Some",
                              "value" : {
                                "type" : "Emv2Propagation",
                                "direction" : {
                                  "type" : "PropagationDirection",
                                  "value" : "Out"
                                },
                                "propagationPoint" : ["FlightSystem_tier1_Instance", "ObservableFailure"],
                                "errorTokens" : ["ServiceOmission"]
                              }
                            }
                          },
                          {
                            "type" : "Emv2Flow",
                            "identifier" : {
                              "type" : "Name",
                              "name" : ["FlightSystem_tier1_Instance", "SatelliteError"]
                            },
                            "kind" : {
                              "type" : "FlowKind",
                              "value" : "Path"
                            },
                            "sourcePropagation" : {
                              "type" : "Some",
                              "value" : {
                                "type" : "Emv2Propagation",
                                "direction" : {
                                  "type" : "PropagationDirection",
                                  "value" : "In"
                                },
                                "propagationPoint" : ["FlightSystem_tier1_Instance", "satelliteSignal"],
                                "errorTokens" : ["NoSignal"]
                              }
                            },
                            "sinkPropagation" : {
                              "type" : "Some",
                              "value" : {
                                "type" : "Emv2Propagation",
                                "direction" : {
                                  "type" : "PropagationDirection",
                                  "value" : "Out"
                                },
                                "propagationPoint" : ["FlightSystem_tier1_Instance", "ObservableFailure"],
                                "errorTokens" : ["ServiceOmission"]
                              }
                            }
                          }
                        ]
                      }
                    }
                  ]
                }
              ],
              "errorLib" : [
                {
                  "type" : "Emv2Library",
                  "name" : {
                    "type" : "Name",
                    "name" : ["ErrorLibrary"]
                  },
                  "useTypes" : [],
                  "tokens" : ["ServiceError", "ItemOmission", "ServiceOmission", "SequenceOmission", "TransientServiceOmission", "LateServiceStart", "EarlyServiceTermination", "BoundedOmissionInterval", "ItemCommission", "ServiceCommission", "SequenceCommission", "EarlyServiceStart", "LateServiceTermination", "ItemTimingError", "EarlyDelivery", "LateDelivery", "SequenceTimingError", "HighRate", "LowRate", "RateJitter", "ServiceTimingError", "DelayedService", "EarlyService", "TimingError", "RateError", "EarlyData", "LateData", "ServiceTimeShift", "ItemValueError", "UndetectableValueError", "DetectableValueError", "OutOfRange", "BelowRange", "AboveRange", "OutOfBounds", "SequenceValueError", "BoundedValueChange", "StuckValue", "OutOfOrder", "ServiceValueError", "OutOfCalibration", "ValueError", "IncorrectValue", "ValueCorruption", "BadValue", "SequenceError", "SubtleValueError", "BenignValueError", "SubtleValueCorruption", "ReplicationError", "AsymmetricReplicatesError", "AsymmetricValue", "AsymmetricApproximateValue", "AsymmetricExactValue", "AsymmetricTiming", "AsymmetricOmission", "AsymmetricItemOmission", "AsymmetricServiceOmission", "SymmetricReplicatesError", "SymmetricValue", "SymmetricApproximateValue", "SymmetricExactValue", "SymmetricTiming", "SymmetricOmission", "SymmetricItemOmission", "SymmetricServiceOmission", "InconsistentValue", "InconsistentTiming", "InconsistentOmission", "InconsistentItemOmission", "InconsistentServiceOmission", "AsymmetricTransmissive", "ConcurrencyError", "RaceCondition", "ReadWriteRace", "WriteWriteRace", "MutExError", "Deadlock", "Starvation"],
                  "alias" : {
                    "type" : "HashMap",
                    "size" : 19,
                    "entries" : [ [ "InconsistentValue", "AsymmetricValue" ],[ "SubtleValueCorruption", "DetectableValueError" ],[ "InconsistentItemOmission", "AsymmetricItemOmission" ],[ "IncorrectValue", "ItemValueError" ],[ "RateError", "SequenceTimingError" ],[ "InconsistentTiming", "AsymmetricTiming" ],[ "TimingError", "ItemTimingError" ],[ "EarlyData", "HighRate" ],[ "ValueError", "ItemValueError" ],[ "LateData", "LowRate" ],[ "BenignValueError", "DetectableValueError" ],[ "ValueCorruption", "ItemValueError" ],[ "SubtleValueError", "UndetectableValueError" ],[ "BadValue", "ItemValueError" ],[ "SequenceError", "SequenceValueError" ],[ "InconsistentOmission", "AsymmetricOmission" ],[ "InconsistentServiceOmission", "AsymmetricServiceOmission" ],[ "AsymmetricTransmissive", "AsymmetricValue" ],[ "ServiceTimeShift", "ServiceTimingError" ] ]
                  }
                },
                {
                  "type" : "Emv2Library",
                  "name" : {
                    "type" : "Name",
                    "name" : ["GPSErrorLibrary"]
                  },
                  "useTypes" : ["ErrorLibrary"],
                  "tokens" : ["SensorFailure", "CPUFailure", "PowerSupplyFailure", "NetworkFailure", "LowPrecisionData", "IncorrectData", "NoSignal", "LowSignal"],
                  "alias" : {
                    "type" : "HashMap",
                    "size" : 0,
                    "entries" : [  ]
                  }
                },
                {
                  "type" : "Emv2Library",
                  "name" : {
                    "type" : "Name",
                    "name" : ["AFGErrorLibrary"]
                  },
                  "useTypes" : ["ErrorLibrary"],
                  "tokens" : ["NoValue", "NoService", "ErraticBehavior", "NoPilotInput", "ErraticPilotInput", "ErraticValue", "FlightSystemfailure"],
                  "alias" : {
                    "type" : "HashMap",
                    "size" : 2,
                    "entries" : [ [ "NoValue", "ServiceOmission" ],[ "NoService", "ServiceOmission" ] ]
                  }
                }
              ]
            }`
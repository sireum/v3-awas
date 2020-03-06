var json = `{
              "type" : "Aadl",
              "components" : [
                {
                  "type" : "Component",
                  "identifier" : {
                    "type" : "Name",
                    "name" : ["top_i_Instance"],
                    "pos" : {
                      "type" : "Some",
                      "value" : {
                        "type" : "Position",
                        "uriOpt" : {
                          "type" : "Some",
                          "value" : "\/Test\/Test.aadl"
                        },
                        "beginLine" : 26,
                        "beginColumn" : 2,
                        "endLine" : 28,
                        "endColumn" : 12,
                        "offset" : 437,
                        "length" : 42
                      }
                    }
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
                      "type" : "FeatureEnd",
                      "identifier" : {
                        "type" : "Name",
                        "name" : ["top_i_Instance", "a"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/Test\/Test.aadl"
                            },
                            "beginLine" : 6,
                            "beginColumn" : 4,
                            "endLine" : 6,
                            "endColumn" : 21,
                            "offset" : 75,
                            "length" : 17
                          }
                        }
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
                      "type" : "FeatureEnd",
                      "identifier" : {
                        "type" : "Name",
                        "name" : ["top_i_Instance", "b"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/Test\/Test.aadl"
                            },
                            "beginLine" : 7,
                            "beginColumn" : 4,
                            "endLine" : 7,
                            "endColumn" : 22,
                            "offset" : 96,
                            "length" : 18
                          }
                        }
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
                    {
                      "type" : "Flow",
                      "name" : {
                        "type" : "Name",
                        "name" : ["top_i_Instance", "af"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/Test\/Test.aadl"
                            },
                            "beginLine" : 9,
                            "beginColumn" : 4,
                            "endLine" : 9,
                            "endColumn" : 27,
                            "offset" : 126,
                            "length" : 23
                          }
                        }
                      },
                      "kind" : {
                        "type" : "FlowKind",
                        "value" : "Path"
                      },
                      "source" : {
                        "type" : "Some",
                        "value" : {
                          "type" : "FeatureEnd",
                          "identifier" : {
                            "type" : "Name",
                            "name" : ["top_i_Instance", "af", "a"],
                            "pos" : {
                              "type" : "Some",
                              "value" : {
                                "type" : "Position",
                                "uriOpt" : {
                                  "type" : "Some",
                                  "value" : "\/Test\/Test.aadl"
                                },
                                "beginLine" : 6,
                                "beginColumn" : 4,
                                "endLine" : 6,
                                "endColumn" : 21,
                                "offset" : 75,
                                "length" : 17
                              }
                            }
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
                      },
                      "sink" : {
                        "type" : "Some",
                        "value" : {
                          "type" : "FeatureEnd",
                          "identifier" : {
                            "type" : "Name",
                            "name" : ["top_i_Instance", "af", "b"],
                            "pos" : {
                              "type" : "Some",
                              "value" : {
                                "type" : "Position",
                                "uriOpt" : {
                                  "type" : "Some",
                                  "value" : "\/Test\/Test.aadl"
                                },
                                "beginLine" : 7,
                                "beginColumn" : 4,
                                "endLine" : 7,
                                "endColumn" : 22,
                                "offset" : 96,
                                "length" : 18
                              }
                            }
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
                      }
                    }
                  ],
                  "modes" : [
                  ],
                  "annexes" : [
                    {
                      "type" : "Annex",
                      "name" : "Emv2",
                      "clause" : {
                        "type" : "Emv2Clause",
                        "libraries" : [
                          {
                            "type" : "Name",
                            "name" : ["ErrorLibrary"],
                            "pos" : {
                              "type" : "Some",
                              "value" : {
                                "type" : "Position",
                                "uriOpt" : {
                                  "type" : "Some",
                                  "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                                },
                                "beginLine" : 6,
                                "beginColumn" : 1,
                                "endLine" : 197,
                                "endColumn" : 14,
                                "offset" : 64,
                                "length" : 6660
                              }
                            }
                          }
                        ],
                        "propagations" : [
                          {
                            "type" : "Emv2Propagation",
                            "direction" : {
                              "type" : "PropagationDirection",
                              "value" : "In"
                            },
                            "propagationPoint" : {
                              "type" : "Name",
                              "name" : ["top_i_Instance", "a"],
                              "pos" : {
                                "type" : "Some",
                                "value" : {
                                  "type" : "Position",
                                  "uriOpt" : {
                                    "type" : "Some",
                                    "value" : "\/Test\/Test.aadl"
                                  },
                                  "beginLine" : 6,
                                  "beginColumn" : 4,
                                  "endLine" : 6,
                                  "endColumn" : 21,
                                  "offset" : 75,
                                  "length" : 17
                                }
                              }
                            },
                            "errorTokens" : [
                              {
                                "type" : "Name",
                                "name" : ["ErrorLibrary", "ServiceError"],
                                "pos" : {
                                  "type" : "Some",
                                  "value" : {
                                    "type" : "Position",
                                    "uriOpt" : {
                                      "type" : "Some",
                                      "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                                    },
                                    "beginLine" : 9,
                                    "beginColumn" : 1,
                                    "endLine" : 9,
                                    "endColumn" : 20,
                                    "offset" : 217,
                                    "length" : 19
                                  }
                                }
                              }
                            ]
                          }
                        ],
                        "flows" : [
                        ],
                        "componentBehavior" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Emv2BehaviorSection",
                            "events" : [
                            ],
                            "transitions" : [
                            ],
                            "propagations" : [
                            ]
                          }
                        },
                        "properties" : [
                        ]
                      }
                    },
                    {
                      "type" : "Annex",
                      "name" : "smf",
                      "clause" : {
                        "type" : "SmfClause",
                        "classification" : [
                          {
                            "type" : "SmfClassification",
                            "portName" : {
                              "type" : "Name",
                              "name" : ["top_i_Instance", "a"],
                              "pos" : {
                                "type" : "Some",
                                "value" : {
                                  "type" : "Position",
                                  "uriOpt" : {
                                    "type" : "Some",
                                    "value" : "\/Test\/Test.aadl"
                                  },
                                  "beginLine" : 6,
                                  "beginColumn" : 4,
                                  "endLine" : 6,
                                  "endColumn" : 21,
                                  "offset" : 75,
                                  "length" : 17
                                }
                              }
                            },
                            "typeName" : {
                              "type" : "Name",
                              "name" : ["top_i_Instance", "High"],
                              "pos" : {
                                "type" : "Some",
                                "value" : {
                                  "type" : "Position",
                                  "uriOpt" : {
                                    "type" : "Some",
                                    "value" : "\/Test\/TestLib.aadl"
                                  },
                                  "beginLine" : 4,
                                  "beginColumn" : 4,
                                  "endLine" : 4,
                                  "endColumn" : 28,
                                  "offset" : 72,
                                  "length" : 24
                                }
                              }
                            }
                          }
                        ],
                        "declass" : [
                          {
                            "type" : "SmfDeclass",
                            "flowName" : {
                              "type" : "Name",
                              "name" : ["top_i_Instance", "af"],
                              "pos" : {
                                "type" : "Some",
                                "value" : {
                                  "type" : "Position",
                                  "uriOpt" : {
                                    "type" : "Some",
                                    "value" : "\/Test\/Test.aadl"
                                  },
                                  "beginLine" : 9,
                                  "beginColumn" : 4,
                                  "endLine" : 9,
                                  "endColumn" : 27,
                                  "offset" : 126,
                                  "length" : 23
                                }
                              }
                            },
                            "srcType" : {
                              "type" : "Some",
                              "value" : {
                                "type" : "Name",
                                "name" : ["top_i_Instance", "High"],
                                "pos" : {
                                  "type" : "Some",
                                  "value" : {
                                    "type" : "Position",
                                    "uriOpt" : {
                                      "type" : "Some",
                                      "value" : "\/Test\/TestLib.aadl"
                                    },
                                    "beginLine" : 4,
                                    "beginColumn" : 4,
                                    "endLine" : 4,
                                    "endColumn" : 28,
                                    "offset" : 72,
                                    "length" : 24
                                  }
                                }
                              }
                            },
                            "snkType" : {
                              "type" : "Name",
                              "name" : ["top_i_Instance", "Low"],
                              "pos" : {
                                "type" : "Some",
                                "value" : {
                                  "type" : "Position",
                                  "uriOpt" : {
                                    "type" : "Some",
                                    "value" : "\/Test\/TestLib.aadl"
                                  },
                                  "beginLine" : 3,
                                  "beginColumn" : 4,
                                  "endLine" : 3,
                                  "endColumn" : 15,
                                  "offset" : 57,
                                  "length" : 11
                                }
                              }
                            }
                          }
                        ]
                      }
                    }
                  ]
                }
              ],
              "annexLib" : [
                {
                  "type" : "Emv2Library",
                  "name" : {
                    "type" : "Name",
                    "name" : ["ErrorLibrary"],
                    "pos" : {
                      "type" : "Some",
                      "value" : {
                        "type" : "Position",
                        "uriOpt" : {
                          "type" : "Some",
                          "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                        },
                        "beginLine" : 6,
                        "beginColumn" : 1,
                        "endLine" : 197,
                        "endColumn" : 14,
                        "offset" : 64,
                        "length" : 6660
                      }
                    }
                  },
                  "useTypes" : [],
                  "errorTypeDef" : [
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "ServiceError"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 9,
                            "beginColumn" : 1,
                            "endLine" : 9,
                            "endColumn" : 20,
                            "offset" : 217,
                            "length" : 19
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "None"
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "ItemOmission"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 10,
                            "beginColumn" : 1,
                            "endLine" : 10,
                            "endColumn" : 41,
                            "offset" : 237,
                            "length" : 40
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "Some",
                        "value" : {
                          "type" : "Name",
                          "name" : ["ErrorLibrary", "ServiceError"],
                          "pos" : {
                            "type" : "Some",
                            "value" : {
                              "type" : "Position",
                              "uriOpt" : {
                                "type" : "Some",
                                "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                              },
                              "beginLine" : 9,
                              "beginColumn" : 1,
                              "endLine" : 9,
                              "endColumn" : 20,
                              "offset" : 217,
                              "length" : 19
                            }
                          }
                        }
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "ServiceOmission"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 11,
                            "beginColumn" : 1,
                            "endLine" : 11,
                            "endColumn" : 44,
                            "offset" : 279,
                            "length" : 43
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "Some",
                        "value" : {
                          "type" : "Name",
                          "name" : ["ErrorLibrary", "ServiceError"],
                          "pos" : {
                            "type" : "Some",
                            "value" : {
                              "type" : "Position",
                              "uriOpt" : {
                                "type" : "Some",
                                "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                              },
                              "beginLine" : 9,
                              "beginColumn" : 1,
                              "endLine" : 9,
                              "endColumn" : 20,
                              "offset" : 217,
                              "length" : 19
                            }
                          }
                        }
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "SequenceOmission"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 12,
                            "beginColumn" : 1,
                            "endLine" : 12,
                            "endColumn" : 45,
                            "offset" : 323,
                            "length" : 44
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "Some",
                        "value" : {
                          "type" : "Name",
                          "name" : ["ErrorLibrary", "ServiceError"],
                          "pos" : {
                            "type" : "Some",
                            "value" : {
                              "type" : "Position",
                              "uriOpt" : {
                                "type" : "Some",
                                "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                              },
                              "beginLine" : 9,
                              "beginColumn" : 1,
                              "endLine" : 9,
                              "endColumn" : 20,
                              "offset" : 217,
                              "length" : 19
                            }
                          }
                        }
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "TransientServiceOmission"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 13,
                            "beginColumn" : 1,
                            "endLine" : 13,
                            "endColumn" : 57,
                            "offset" : 368,
                            "length" : 56
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "Some",
                        "value" : {
                          "type" : "Name",
                          "name" : ["ErrorLibrary", "SequenceOmission"],
                          "pos" : {
                            "type" : "Some",
                            "value" : {
                              "type" : "Position",
                              "uriOpt" : {
                                "type" : "Some",
                                "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                              },
                              "beginLine" : 12,
                              "beginColumn" : 1,
                              "endLine" : 12,
                              "endColumn" : 45,
                              "offset" : 323,
                              "length" : 44
                            }
                          }
                        }
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "LateServiceStart"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 14,
                            "beginColumn" : 1,
                            "endLine" : 14,
                            "endColumn" : 49,
                            "offset" : 425,
                            "length" : 48
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "Some",
                        "value" : {
                          "type" : "Name",
                          "name" : ["ErrorLibrary", "SequenceOmission"],
                          "pos" : {
                            "type" : "Some",
                            "value" : {
                              "type" : "Position",
                              "uriOpt" : {
                                "type" : "Some",
                                "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                              },
                              "beginLine" : 12,
                              "beginColumn" : 1,
                              "endLine" : 12,
                              "endColumn" : 45,
                              "offset" : 323,
                              "length" : 44
                            }
                          }
                        }
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "EarlyServiceTermination"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 15,
                            "beginColumn" : 1,
                            "endLine" : 15,
                            "endColumn" : 56,
                            "offset" : 474,
                            "length" : 55
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "Some",
                        "value" : {
                          "type" : "Name",
                          "name" : ["ErrorLibrary", "SequenceOmission"],
                          "pos" : {
                            "type" : "Some",
                            "value" : {
                              "type" : "Position",
                              "uriOpt" : {
                                "type" : "Some",
                                "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                              },
                              "beginLine" : 12,
                              "beginColumn" : 1,
                              "endLine" : 12,
                              "endColumn" : 45,
                              "offset" : 323,
                              "length" : 44
                            }
                          }
                        }
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "BoundedOmissionInterval"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 16,
                            "beginColumn" : 1,
                            "endLine" : 16,
                            "endColumn" : 56,
                            "offset" : 530,
                            "length" : 55
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "Some",
                        "value" : {
                          "type" : "Name",
                          "name" : ["ErrorLibrary", "SequenceOmission"],
                          "pos" : {
                            "type" : "Some",
                            "value" : {
                              "type" : "Position",
                              "uriOpt" : {
                                "type" : "Some",
                                "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                              },
                              "beginLine" : 12,
                              "beginColumn" : 1,
                              "endLine" : 12,
                              "endColumn" : 45,
                              "offset" : 323,
                              "length" : 44
                            }
                          }
                        }
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "ItemCommission"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 17,
                            "beginColumn" : 1,
                            "endLine" : 17,
                            "endColumn" : 43,
                            "offset" : 587,
                            "length" : 42
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "Some",
                        "value" : {
                          "type" : "Name",
                          "name" : ["ErrorLibrary", "ServiceError"],
                          "pos" : {
                            "type" : "Some",
                            "value" : {
                              "type" : "Position",
                              "uriOpt" : {
                                "type" : "Some",
                                "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                              },
                              "beginLine" : 9,
                              "beginColumn" : 1,
                              "endLine" : 9,
                              "endColumn" : 20,
                              "offset" : 217,
                              "length" : 19
                            }
                          }
                        }
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "ServiceCommission"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 18,
                            "beginColumn" : 1,
                            "endLine" : 18,
                            "endColumn" : 46,
                            "offset" : 631,
                            "length" : 45
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "Some",
                        "value" : {
                          "type" : "Name",
                          "name" : ["ErrorLibrary", "ServiceError"],
                          "pos" : {
                            "type" : "Some",
                            "value" : {
                              "type" : "Position",
                              "uriOpt" : {
                                "type" : "Some",
                                "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                              },
                              "beginLine" : 9,
                              "beginColumn" : 1,
                              "endLine" : 9,
                              "endColumn" : 20,
                              "offset" : 217,
                              "length" : 19
                            }
                          }
                        }
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "SequenceCommission"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 19,
                            "beginColumn" : 1,
                            "endLine" : 19,
                            "endColumn" : 47,
                            "offset" : 677,
                            "length" : 46
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "Some",
                        "value" : {
                          "type" : "Name",
                          "name" : ["ErrorLibrary", "ServiceError"],
                          "pos" : {
                            "type" : "Some",
                            "value" : {
                              "type" : "Position",
                              "uriOpt" : {
                                "type" : "Some",
                                "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                              },
                              "beginLine" : 9,
                              "beginColumn" : 1,
                              "endLine" : 9,
                              "endColumn" : 20,
                              "offset" : 217,
                              "length" : 19
                            }
                          }
                        }
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "EarlyServiceStart"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 20,
                            "beginColumn" : 1,
                            "endLine" : 20,
                            "endColumn" : 52,
                            "offset" : 724,
                            "length" : 51
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "Some",
                        "value" : {
                          "type" : "Name",
                          "name" : ["ErrorLibrary", "SequenceCommission"],
                          "pos" : {
                            "type" : "Some",
                            "value" : {
                              "type" : "Position",
                              "uriOpt" : {
                                "type" : "Some",
                                "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                              },
                              "beginLine" : 19,
                              "beginColumn" : 1,
                              "endLine" : 19,
                              "endColumn" : 47,
                              "offset" : 677,
                              "length" : 46
                            }
                          }
                        }
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "LateServiceTermination"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 21,
                            "beginColumn" : 1,
                            "endLine" : 21,
                            "endColumn" : 57,
                            "offset" : 776,
                            "length" : 56
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "Some",
                        "value" : {
                          "type" : "Name",
                          "name" : ["ErrorLibrary", "SequenceCommission"],
                          "pos" : {
                            "type" : "Some",
                            "value" : {
                              "type" : "Position",
                              "uriOpt" : {
                                "type" : "Some",
                                "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                              },
                              "beginLine" : 19,
                              "beginColumn" : 1,
                              "endLine" : 19,
                              "endColumn" : 47,
                              "offset" : 677,
                              "length" : 46
                            }
                          }
                        }
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "ItemTimingError"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 26,
                            "beginColumn" : 1,
                            "endLine" : 26,
                            "endColumn" : 23,
                            "offset" : 969,
                            "length" : 22
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "None"
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "EarlyDelivery"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 27,
                            "beginColumn" : 1,
                            "endLine" : 27,
                            "endColumn" : 45,
                            "offset" : 993,
                            "length" : 44
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "Some",
                        "value" : {
                          "type" : "Name",
                          "name" : ["ErrorLibrary", "ItemTimingError"],
                          "pos" : {
                            "type" : "Some",
                            "value" : {
                              "type" : "Position",
                              "uriOpt" : {
                                "type" : "Some",
                                "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                              },
                              "beginLine" : 26,
                              "beginColumn" : 1,
                              "endLine" : 26,
                              "endColumn" : 23,
                              "offset" : 969,
                              "length" : 22
                            }
                          }
                        }
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "LateDelivery"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 28,
                            "beginColumn" : 1,
                            "endLine" : 28,
                            "endColumn" : 44,
                            "offset" : 1039,
                            "length" : 43
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "Some",
                        "value" : {
                          "type" : "Name",
                          "name" : ["ErrorLibrary", "ItemTimingError"],
                          "pos" : {
                            "type" : "Some",
                            "value" : {
                              "type" : "Position",
                              "uriOpt" : {
                                "type" : "Some",
                                "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                              },
                              "beginLine" : 26,
                              "beginColumn" : 1,
                              "endLine" : 26,
                              "endColumn" : 23,
                              "offset" : 969,
                              "length" : 22
                            }
                          }
                        }
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "SequenceTimingError"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 30,
                            "beginColumn" : 1,
                            "endLine" : 30,
                            "endColumn" : 27,
                            "offset" : 1114,
                            "length" : 26
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "None"
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "HighRate"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 31,
                            "beginColumn" : 1,
                            "endLine" : 31,
                            "endColumn" : 44,
                            "offset" : 1142,
                            "length" : 43
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "Some",
                        "value" : {
                          "type" : "Name",
                          "name" : ["ErrorLibrary", "SequenceTimingError"],
                          "pos" : {
                            "type" : "Some",
                            "value" : {
                              "type" : "Position",
                              "uriOpt" : {
                                "type" : "Some",
                                "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                              },
                              "beginLine" : 30,
                              "beginColumn" : 1,
                              "endLine" : 30,
                              "endColumn" : 27,
                              "offset" : 1114,
                              "length" : 26
                            }
                          }
                        }
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "LowRate"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 32,
                            "beginColumn" : 1,
                            "endLine" : 32,
                            "endColumn" : 43,
                            "offset" : 1187,
                            "length" : 42
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "Some",
                        "value" : {
                          "type" : "Name",
                          "name" : ["ErrorLibrary", "SequenceTimingError"],
                          "pos" : {
                            "type" : "Some",
                            "value" : {
                              "type" : "Position",
                              "uriOpt" : {
                                "type" : "Some",
                                "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                              },
                              "beginLine" : 30,
                              "beginColumn" : 1,
                              "endLine" : 30,
                              "endColumn" : 27,
                              "offset" : 1114,
                              "length" : 26
                            }
                          }
                        }
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "RateJitter"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 33,
                            "beginColumn" : 1,
                            "endLine" : 33,
                            "endColumn" : 46,
                            "offset" : 1230,
                            "length" : 45
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "Some",
                        "value" : {
                          "type" : "Name",
                          "name" : ["ErrorLibrary", "SequenceTimingError"],
                          "pos" : {
                            "type" : "Some",
                            "value" : {
                              "type" : "Position",
                              "uriOpt" : {
                                "type" : "Some",
                                "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                              },
                              "beginLine" : 30,
                              "beginColumn" : 1,
                              "endLine" : 30,
                              "endColumn" : 27,
                              "offset" : 1114,
                              "length" : 26
                            }
                          }
                        }
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "ServiceTimingError"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 35,
                            "beginColumn" : 1,
                            "endLine" : 35,
                            "endColumn" : 26,
                            "offset" : 1300,
                            "length" : 25
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "None"
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "DelayedService"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 36,
                            "beginColumn" : 1,
                            "endLine" : 36,
                            "endColumn" : 49,
                            "offset" : 1327,
                            "length" : 48
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "Some",
                        "value" : {
                          "type" : "Name",
                          "name" : ["ErrorLibrary", "ServiceTimingError"],
                          "pos" : {
                            "type" : "Some",
                            "value" : {
                              "type" : "Position",
                              "uriOpt" : {
                                "type" : "Some",
                                "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                              },
                              "beginLine" : 35,
                              "beginColumn" : 1,
                              "endLine" : 35,
                              "endColumn" : 26,
                              "offset" : 1300,
                              "length" : 25
                            }
                          }
                        }
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "EarlyService"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 37,
                            "beginColumn" : 1,
                            "endLine" : 37,
                            "endColumn" : 47,
                            "offset" : 1377,
                            "length" : 46
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "Some",
                        "value" : {
                          "type" : "Name",
                          "name" : ["ErrorLibrary", "ServiceTimingError"],
                          "pos" : {
                            "type" : "Some",
                            "value" : {
                              "type" : "Position",
                              "uriOpt" : {
                                "type" : "Some",
                                "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                              },
                              "beginLine" : 35,
                              "beginColumn" : 1,
                              "endLine" : 35,
                              "endColumn" : 26,
                              "offset" : 1300,
                              "length" : 25
                            }
                          }
                        }
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "TimingError"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 40,
                            "beginColumn" : 1,
                            "endLine" : 40,
                            "endColumn" : 42,
                            "offset" : 1455,
                            "length" : 41
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "None"
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "RateError"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 41,
                            "beginColumn" : 1,
                            "endLine" : 41,
                            "endColumn" : 44,
                            "offset" : 1507,
                            "length" : 43
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "None"
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "EarlyData"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 42,
                            "beginColumn" : 1,
                            "endLine" : 42,
                            "endColumn" : 33,
                            "offset" : 1551,
                            "length" : 32
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "None"
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "LateData"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 43,
                            "beginColumn" : 1,
                            "endLine" : 43,
                            "endColumn" : 31,
                            "offset" : 1584,
                            "length" : 30
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "None"
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "ServiceTimeShift"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 44,
                            "beginColumn" : 1,
                            "endLine" : 44,
                            "endColumn" : 50,
                            "offset" : 1615,
                            "length" : 49
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "None"
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "ItemValueError"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 49,
                            "beginColumn" : 1,
                            "endLine" : 49,
                            "endColumn" : 22,
                            "offset" : 1796,
                            "length" : 21
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "None"
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "UndetectableValueError"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 50,
                            "beginColumn" : 1,
                            "endLine" : 50,
                            "endColumn" : 53,
                            "offset" : 1819,
                            "length" : 52
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "Some",
                        "value" : {
                          "type" : "Name",
                          "name" : ["ErrorLibrary", "ItemValueError"],
                          "pos" : {
                            "type" : "Some",
                            "value" : {
                              "type" : "Position",
                              "uriOpt" : {
                                "type" : "Some",
                                "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                              },
                              "beginLine" : 49,
                              "beginColumn" : 1,
                              "endLine" : 49,
                              "endColumn" : 22,
                              "offset" : 1796,
                              "length" : 21
                            }
                          }
                        }
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "DetectableValueError"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 51,
                            "beginColumn" : 1,
                            "endLine" : 51,
                            "endColumn" : 51,
                            "offset" : 1873,
                            "length" : 50
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "Some",
                        "value" : {
                          "type" : "Name",
                          "name" : ["ErrorLibrary", "ItemValueError"],
                          "pos" : {
                            "type" : "Some",
                            "value" : {
                              "type" : "Position",
                              "uriOpt" : {
                                "type" : "Some",
                                "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                              },
                              "beginLine" : 49,
                              "beginColumn" : 1,
                              "endLine" : 49,
                              "endColumn" : 22,
                              "offset" : 1796,
                              "length" : 21
                            }
                          }
                        }
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "OutOfRange"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 52,
                            "beginColumn" : 1,
                            "endLine" : 52,
                            "endColumn" : 47,
                            "offset" : 1925,
                            "length" : 46
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "Some",
                        "value" : {
                          "type" : "Name",
                          "name" : ["ErrorLibrary", "DetectableValueError"],
                          "pos" : {
                            "type" : "Some",
                            "value" : {
                              "type" : "Position",
                              "uriOpt" : {
                                "type" : "Some",
                                "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                              },
                              "beginLine" : 51,
                              "beginColumn" : 1,
                              "endLine" : 51,
                              "endColumn" : 51,
                              "offset" : 1873,
                              "length" : 50
                            }
                          }
                        }
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "BelowRange"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 53,
                            "beginColumn" : 1,
                            "endLine" : 53,
                            "endColumn" : 37,
                            "offset" : 1972,
                            "length" : 36
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "Some",
                        "value" : {
                          "type" : "Name",
                          "name" : ["ErrorLibrary", "OutOfRange"],
                          "pos" : {
                            "type" : "Some",
                            "value" : {
                              "type" : "Position",
                              "uriOpt" : {
                                "type" : "Some",
                                "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                              },
                              "beginLine" : 52,
                              "beginColumn" : 1,
                              "endLine" : 52,
                              "endColumn" : 47,
                              "offset" : 1925,
                              "length" : 46
                            }
                          }
                        }
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "AboveRange"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 54,
                            "beginColumn" : 1,
                            "endLine" : 54,
                            "endColumn" : 37,
                            "offset" : 2009,
                            "length" : 36
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "Some",
                        "value" : {
                          "type" : "Name",
                          "name" : ["ErrorLibrary", "OutOfRange"],
                          "pos" : {
                            "type" : "Some",
                            "value" : {
                              "type" : "Position",
                              "uriOpt" : {
                                "type" : "Some",
                                "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                              },
                              "beginLine" : 52,
                              "beginColumn" : 1,
                              "endLine" : 52,
                              "endColumn" : 47,
                              "offset" : 1925,
                              "length" : 46
                            }
                          }
                        }
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "OutOfBounds"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 55,
                            "beginColumn" : 1,
                            "endLine" : 55,
                            "endColumn" : 48,
                            "offset" : 2046,
                            "length" : 47
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "Some",
                        "value" : {
                          "type" : "Name",
                          "name" : ["ErrorLibrary", "DetectableValueError"],
                          "pos" : {
                            "type" : "Some",
                            "value" : {
                              "type" : "Position",
                              "uriOpt" : {
                                "type" : "Some",
                                "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                              },
                              "beginLine" : 51,
                              "beginColumn" : 1,
                              "endLine" : 51,
                              "endColumn" : 51,
                              "offset" : 1873,
                              "length" : 50
                            }
                          }
                        }
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "SequenceValueError"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 57,
                            "beginColumn" : 1,
                            "endLine" : 57,
                            "endColumn" : 26,
                            "offset" : 2113,
                            "length" : 25
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "None"
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "BoundedValueChange"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 58,
                            "beginColumn" : 1,
                            "endLine" : 58,
                            "endColumn" : 48,
                            "offset" : 2139,
                            "length" : 47
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "Some",
                        "value" : {
                          "type" : "Name",
                          "name" : ["ErrorLibrary", "SequenceError"],
                          "pos" : {
                            "type" : "Some",
                            "value" : {
                              "type" : "Position",
                              "uriOpt" : {
                                "type" : "Some",
                                "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                              },
                              "beginLine" : 70,
                              "beginColumn" : 1,
                              "endLine" : 70,
                              "endColumn" : 47,
                              "offset" : 2558,
                              "length" : 46
                            }
                          }
                        }
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "StuckValue"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 59,
                            "beginColumn" : 1,
                            "endLine" : 59,
                            "endColumn" : 40,
                            "offset" : 2188,
                            "length" : 39
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "Some",
                        "value" : {
                          "type" : "Name",
                          "name" : ["ErrorLibrary", "SequenceError"],
                          "pos" : {
                            "type" : "Some",
                            "value" : {
                              "type" : "Position",
                              "uriOpt" : {
                                "type" : "Some",
                                "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                              },
                              "beginLine" : 70,
                              "beginColumn" : 1,
                              "endLine" : 70,
                              "endColumn" : 47,
                              "offset" : 2558,
                              "length" : 46
                            }
                          }
                        }
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "OutOfOrder"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 60,
                            "beginColumn" : 1,
                            "endLine" : 60,
                            "endColumn" : 40,
                            "offset" : 2229,
                            "length" : 39
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "Some",
                        "value" : {
                          "type" : "Name",
                          "name" : ["ErrorLibrary", "SequenceError"],
                          "pos" : {
                            "type" : "Some",
                            "value" : {
                              "type" : "Position",
                              "uriOpt" : {
                                "type" : "Some",
                                "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                              },
                              "beginLine" : 70,
                              "beginColumn" : 1,
                              "endLine" : 70,
                              "endColumn" : 47,
                              "offset" : 2558,
                              "length" : 46
                            }
                          }
                        }
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "ServiceValueError"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 62,
                            "beginColumn" : 1,
                            "endLine" : 62,
                            "endColumn" : 25,
                            "offset" : 2271,
                            "length" : 24
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "None"
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "OutOfCalibration"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 63,
                            "beginColumn" : 1,
                            "endLine" : 63,
                            "endColumn" : 50,
                            "offset" : 2296,
                            "length" : 49
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "Some",
                        "value" : {
                          "type" : "Name",
                          "name" : ["ErrorLibrary", "ServiceValueError"],
                          "pos" : {
                            "type" : "Some",
                            "value" : {
                              "type" : "Position",
                              "uriOpt" : {
                                "type" : "Some",
                                "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                              },
                              "beginLine" : 62,
                              "beginColumn" : 1,
                              "endLine" : 62,
                              "endColumn" : 25,
                              "offset" : 2271,
                              "length" : 24
                            }
                          }
                        }
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "ValueError"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 66,
                            "beginColumn" : 1,
                            "endLine" : 66,
                            "endColumn" : 40,
                            "offset" : 2391,
                            "length" : 39
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "None"
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "IncorrectValue"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 67,
                            "beginColumn" : 1,
                            "endLine" : 67,
                            "endColumn" : 44,
                            "offset" : 2431,
                            "length" : 43
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "None"
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "ValueCorruption"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 68,
                            "beginColumn" : 1,
                            "endLine" : 68,
                            "endColumn" : 45,
                            "offset" : 2475,
                            "length" : 44
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "None"
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "BadValue"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 69,
                            "beginColumn" : 1,
                            "endLine" : 69,
                            "endColumn" : 38,
                            "offset" : 2520,
                            "length" : 37
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "None"
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "SequenceError"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 70,
                            "beginColumn" : 1,
                            "endLine" : 70,
                            "endColumn" : 47,
                            "offset" : 2558,
                            "length" : 46
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "None"
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "SubtleValueError"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 72,
                            "beginColumn" : 1,
                            "endLine" : 72,
                            "endColumn" : 54,
                            "offset" : 2606,
                            "length" : 53
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "None"
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "BenignValueError"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 73,
                            "beginColumn" : 1,
                            "endLine" : 73,
                            "endColumn" : 52,
                            "offset" : 2660,
                            "length" : 51
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "None"
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "SubtleValueCorruption"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 74,
                            "beginColumn" : 1,
                            "endLine" : 74,
                            "endColumn" : 57,
                            "offset" : 2712,
                            "length" : 56
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "None"
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "ReplicationError"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 78,
                            "beginColumn" : 1,
                            "endLine" : 78,
                            "endColumn" : 24,
                            "offset" : 2866,
                            "length" : 23
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "None"
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "AsymmetricReplicatesError"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 79,
                            "beginColumn" : 1,
                            "endLine" : 79,
                            "endColumn" : 58,
                            "offset" : 2890,
                            "length" : 57
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "Some",
                        "value" : {
                          "type" : "Name",
                          "name" : ["ErrorLibrary", "ReplicationError"],
                          "pos" : {
                            "type" : "Some",
                            "value" : {
                              "type" : "Position",
                              "uriOpt" : {
                                "type" : "Some",
                                "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                              },
                              "beginLine" : 78,
                              "beginColumn" : 1,
                              "endLine" : 78,
                              "endColumn" : 24,
                              "offset" : 2866,
                              "length" : 23
                            }
                          }
                        }
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "AsymmetricValue"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 80,
                            "beginColumn" : 1,
                            "endLine" : 80,
                            "endColumn" : 57,
                            "offset" : 2948,
                            "length" : 56
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "Some",
                        "value" : {
                          "type" : "Name",
                          "name" : ["ErrorLibrary", "AsymmetricReplicatesError"],
                          "pos" : {
                            "type" : "Some",
                            "value" : {
                              "type" : "Position",
                              "uriOpt" : {
                                "type" : "Some",
                                "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                              },
                              "beginLine" : 79,
                              "beginColumn" : 1,
                              "endLine" : 79,
                              "endColumn" : 58,
                              "offset" : 2890,
                              "length" : 57
                            }
                          }
                        }
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "AsymmetricApproximateValue"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 81,
                            "beginColumn" : 1,
                            "endLine" : 81,
                            "endColumn" : 58,
                            "offset" : 3006,
                            "length" : 57
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "Some",
                        "value" : {
                          "type" : "Name",
                          "name" : ["ErrorLibrary", "AsymmetricValue"],
                          "pos" : {
                            "type" : "Some",
                            "value" : {
                              "type" : "Position",
                              "uriOpt" : {
                                "type" : "Some",
                                "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                              },
                              "beginLine" : 80,
                              "beginColumn" : 1,
                              "endLine" : 80,
                              "endColumn" : 57,
                              "offset" : 2948,
                              "length" : 56
                            }
                          }
                        }
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "AsymmetricExactValue"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 82,
                            "beginColumn" : 1,
                            "endLine" : 82,
                            "endColumn" : 52,
                            "offset" : 3064,
                            "length" : 51
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "Some",
                        "value" : {
                          "type" : "Name",
                          "name" : ["ErrorLibrary", "AsymmetricValue"],
                          "pos" : {
                            "type" : "Some",
                            "value" : {
                              "type" : "Position",
                              "uriOpt" : {
                                "type" : "Some",
                                "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                              },
                              "beginLine" : 80,
                              "beginColumn" : 1,
                              "endLine" : 80,
                              "endColumn" : 57,
                              "offset" : 2948,
                              "length" : 56
                            }
                          }
                        }
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "AsymmetricTiming"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 83,
                            "beginColumn" : 1,
                            "endLine" : 83,
                            "endColumn" : 58,
                            "offset" : 3116,
                            "length" : 57
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "Some",
                        "value" : {
                          "type" : "Name",
                          "name" : ["ErrorLibrary", "AsymmetricReplicatesError"],
                          "pos" : {
                            "type" : "Some",
                            "value" : {
                              "type" : "Position",
                              "uriOpt" : {
                                "type" : "Some",
                                "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                              },
                              "beginLine" : 79,
                              "beginColumn" : 1,
                              "endLine" : 79,
                              "endColumn" : 58,
                              "offset" : 2890,
                              "length" : 57
                            }
                          }
                        }
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "AsymmetricOmission"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 84,
                            "beginColumn" : 1,
                            "endLine" : 84,
                            "endColumn" : 60,
                            "offset" : 3175,
                            "length" : 59
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "Some",
                        "value" : {
                          "type" : "Name",
                          "name" : ["ErrorLibrary", "AsymmetricReplicatesError"],
                          "pos" : {
                            "type" : "Some",
                            "value" : {
                              "type" : "Position",
                              "uriOpt" : {
                                "type" : "Some",
                                "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                              },
                              "beginLine" : 79,
                              "beginColumn" : 1,
                              "endLine" : 79,
                              "endColumn" : 58,
                              "offset" : 2890,
                              "length" : 57
                            }
                          }
                        }
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "AsymmetricItemOmission"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 85,
                            "beginColumn" : 1,
                            "endLine" : 85,
                            "endColumn" : 57,
                            "offset" : 3235,
                            "length" : 56
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "Some",
                        "value" : {
                          "type" : "Name",
                          "name" : ["ErrorLibrary", "AsymmetricOmission"],
                          "pos" : {
                            "type" : "Some",
                            "value" : {
                              "type" : "Position",
                              "uriOpt" : {
                                "type" : "Some",
                                "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                              },
                              "beginLine" : 84,
                              "beginColumn" : 1,
                              "endLine" : 84,
                              "endColumn" : 60,
                              "offset" : 3175,
                              "length" : 59
                            }
                          }
                        }
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "AsymmetricServiceOmission"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 86,
                            "beginColumn" : 1,
                            "endLine" : 86,
                            "endColumn" : 60,
                            "offset" : 3292,
                            "length" : 59
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "Some",
                        "value" : {
                          "type" : "Name",
                          "name" : ["ErrorLibrary", "AsymmetricOmission"],
                          "pos" : {
                            "type" : "Some",
                            "value" : {
                              "type" : "Position",
                              "uriOpt" : {
                                "type" : "Some",
                                "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                              },
                              "beginLine" : 84,
                              "beginColumn" : 1,
                              "endLine" : 84,
                              "endColumn" : 60,
                              "offset" : 3175,
                              "length" : 59
                            }
                          }
                        }
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "SymmetricReplicatesError"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 88,
                            "beginColumn" : 1,
                            "endLine" : 88,
                            "endColumn" : 57,
                            "offset" : 3353,
                            "length" : 56
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "Some",
                        "value" : {
                          "type" : "Name",
                          "name" : ["ErrorLibrary", "ReplicationError"],
                          "pos" : {
                            "type" : "Some",
                            "value" : {
                              "type" : "Position",
                              "uriOpt" : {
                                "type" : "Some",
                                "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                              },
                              "beginLine" : 78,
                              "beginColumn" : 1,
                              "endLine" : 78,
                              "endColumn" : 24,
                              "offset" : 2866,
                              "length" : 23
                            }
                          }
                        }
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "SymmetricValue"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 89,
                            "beginColumn" : 1,
                            "endLine" : 89,
                            "endColumn" : 55,
                            "offset" : 3410,
                            "length" : 54
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "Some",
                        "value" : {
                          "type" : "Name",
                          "name" : ["ErrorLibrary", "SymmetricReplicatesError"],
                          "pos" : {
                            "type" : "Some",
                            "value" : {
                              "type" : "Position",
                              "uriOpt" : {
                                "type" : "Some",
                                "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                              },
                              "beginLine" : 88,
                              "beginColumn" : 1,
                              "endLine" : 88,
                              "endColumn" : 57,
                              "offset" : 3353,
                              "length" : 56
                            }
                          }
                        }
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "SymmetricApproximateValue"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 90,
                            "beginColumn" : 1,
                            "endLine" : 90,
                            "endColumn" : 56,
                            "offset" : 3466,
                            "length" : 55
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "Some",
                        "value" : {
                          "type" : "Name",
                          "name" : ["ErrorLibrary", "SymmetricValue"],
                          "pos" : {
                            "type" : "Some",
                            "value" : {
                              "type" : "Position",
                              "uriOpt" : {
                                "type" : "Some",
                                "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                              },
                              "beginLine" : 89,
                              "beginColumn" : 1,
                              "endLine" : 89,
                              "endColumn" : 55,
                              "offset" : 3410,
                              "length" : 54
                            }
                          }
                        }
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "SymmetricExactValue"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 91,
                            "beginColumn" : 1,
                            "endLine" : 91,
                            "endColumn" : 50,
                            "offset" : 3522,
                            "length" : 49
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "Some",
                        "value" : {
                          "type" : "Name",
                          "name" : ["ErrorLibrary", "SymmetricValue"],
                          "pos" : {
                            "type" : "Some",
                            "value" : {
                              "type" : "Position",
                              "uriOpt" : {
                                "type" : "Some",
                                "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                              },
                              "beginLine" : 89,
                              "beginColumn" : 1,
                              "endLine" : 89,
                              "endColumn" : 55,
                              "offset" : 3410,
                              "length" : 54
                            }
                          }
                        }
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "SymmetricTiming"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 92,
                            "beginColumn" : 1,
                            "endLine" : 92,
                            "endColumn" : 56,
                            "offset" : 3572,
                            "length" : 55
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "Some",
                        "value" : {
                          "type" : "Name",
                          "name" : ["ErrorLibrary", "SymmetricReplicatesError"],
                          "pos" : {
                            "type" : "Some",
                            "value" : {
                              "type" : "Position",
                              "uriOpt" : {
                                "type" : "Some",
                                "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                              },
                              "beginLine" : 88,
                              "beginColumn" : 1,
                              "endLine" : 88,
                              "endColumn" : 57,
                              "offset" : 3353,
                              "length" : 56
                            }
                          }
                        }
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "SymmetricOmission"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 93,
                            "beginColumn" : 1,
                            "endLine" : 93,
                            "endColumn" : 58,
                            "offset" : 3629,
                            "length" : 57
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "Some",
                        "value" : {
                          "type" : "Name",
                          "name" : ["ErrorLibrary", "SymmetricReplicatesError"],
                          "pos" : {
                            "type" : "Some",
                            "value" : {
                              "type" : "Position",
                              "uriOpt" : {
                                "type" : "Some",
                                "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                              },
                              "beginLine" : 88,
                              "beginColumn" : 1,
                              "endLine" : 88,
                              "endColumn" : 57,
                              "offset" : 3353,
                              "length" : 56
                            }
                          }
                        }
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "SymmetricItemOmission"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 94,
                            "beginColumn" : 1,
                            "endLine" : 94,
                            "endColumn" : 55,
                            "offset" : 3687,
                            "length" : 54
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "Some",
                        "value" : {
                          "type" : "Name",
                          "name" : ["ErrorLibrary", "SymmetricOmission"],
                          "pos" : {
                            "type" : "Some",
                            "value" : {
                              "type" : "Position",
                              "uriOpt" : {
                                "type" : "Some",
                                "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                              },
                              "beginLine" : 93,
                              "beginColumn" : 1,
                              "endLine" : 93,
                              "endColumn" : 58,
                              "offset" : 3629,
                              "length" : 57
                            }
                          }
                        }
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "SymmetricServiceOmission"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 95,
                            "beginColumn" : 1,
                            "endLine" : 95,
                            "endColumn" : 58,
                            "offset" : 3742,
                            "length" : 57
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "Some",
                        "value" : {
                          "type" : "Name",
                          "name" : ["ErrorLibrary", "SymmetricOmission"],
                          "pos" : {
                            "type" : "Some",
                            "value" : {
                              "type" : "Position",
                              "uriOpt" : {
                                "type" : "Some",
                                "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                              },
                              "beginLine" : 93,
                              "beginColumn" : 1,
                              "endLine" : 93,
                              "endColumn" : 58,
                              "offset" : 3629,
                              "length" : 57
                            }
                          }
                        }
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "InconsistentValue"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 98,
                            "beginColumn" : 1,
                            "endLine" : 98,
                            "endColumn" : 48,
                            "offset" : 3828,
                            "length" : 47
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "None"
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "InconsistentTiming"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 99,
                            "beginColumn" : 1,
                            "endLine" : 99,
                            "endColumn" : 50,
                            "offset" : 3876,
                            "length" : 49
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "None"
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "InconsistentOmission"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 100,
                            "beginColumn" : 1,
                            "endLine" : 100,
                            "endColumn" : 54,
                            "offset" : 3926,
                            "length" : 53
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "None"
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "InconsistentItemOmission"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 101,
                            "beginColumn" : 1,
                            "endLine" : 101,
                            "endColumn" : 62,
                            "offset" : 3980,
                            "length" : 61
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "None"
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "InconsistentServiceOmission"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 102,
                            "beginColumn" : 1,
                            "endLine" : 102,
                            "endColumn" : 68,
                            "offset" : 4042,
                            "length" : 67
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "None"
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "AsymmetricTransmissive"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 103,
                            "beginColumn" : 1,
                            "endLine" : 103,
                            "endColumn" : 53,
                            "offset" : 4110,
                            "length" : 52
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "None"
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "ConcurrencyError"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 106,
                            "beginColumn" : 1,
                            "endLine" : 106,
                            "endColumn" : 24,
                            "offset" : 4185,
                            "length" : 23
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "None"
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "RaceCondition"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 107,
                            "beginColumn" : 1,
                            "endLine" : 107,
                            "endColumn" : 46,
                            "offset" : 4209,
                            "length" : 45
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "Some",
                        "value" : {
                          "type" : "Name",
                          "name" : ["ErrorLibrary", "ConcurrencyError"],
                          "pos" : {
                            "type" : "Some",
                            "value" : {
                              "type" : "Position",
                              "uriOpt" : {
                                "type" : "Some",
                                "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                              },
                              "beginLine" : 106,
                              "beginColumn" : 1,
                              "endLine" : 106,
                              "endColumn" : 24,
                              "offset" : 4185,
                              "length" : 23
                            }
                          }
                        }
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "ReadWriteRace"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 108,
                            "beginColumn" : 1,
                            "endLine" : 108,
                            "endColumn" : 43,
                            "offset" : 4255,
                            "length" : 42
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "Some",
                        "value" : {
                          "type" : "Name",
                          "name" : ["ErrorLibrary", "RaceCondition"],
                          "pos" : {
                            "type" : "Some",
                            "value" : {
                              "type" : "Position",
                              "uriOpt" : {
                                "type" : "Some",
                                "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                              },
                              "beginLine" : 107,
                              "beginColumn" : 1,
                              "endLine" : 107,
                              "endColumn" : 46,
                              "offset" : 4209,
                              "length" : 45
                            }
                          }
                        }
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "WriteWriteRace"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 109,
                            "beginColumn" : 1,
                            "endLine" : 109,
                            "endColumn" : 44,
                            "offset" : 4298,
                            "length" : 43
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "Some",
                        "value" : {
                          "type" : "Name",
                          "name" : ["ErrorLibrary", "RaceCondition"],
                          "pos" : {
                            "type" : "Some",
                            "value" : {
                              "type" : "Position",
                              "uriOpt" : {
                                "type" : "Some",
                                "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                              },
                              "beginLine" : 107,
                              "beginColumn" : 1,
                              "endLine" : 107,
                              "endColumn" : 46,
                              "offset" : 4209,
                              "length" : 45
                            }
                          }
                        }
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "MutExError"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 110,
                            "beginColumn" : 1,
                            "endLine" : 110,
                            "endColumn" : 43,
                            "offset" : 4342,
                            "length" : 42
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "Some",
                        "value" : {
                          "type" : "Name",
                          "name" : ["ErrorLibrary", "ConcurrencyError"],
                          "pos" : {
                            "type" : "Some",
                            "value" : {
                              "type" : "Position",
                              "uriOpt" : {
                                "type" : "Some",
                                "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                              },
                              "beginLine" : 106,
                              "beginColumn" : 1,
                              "endLine" : 106,
                              "endColumn" : 24,
                              "offset" : 4185,
                              "length" : 23
                            }
                          }
                        }
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "Deadlock"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 111,
                            "beginColumn" : 1,
                            "endLine" : 111,
                            "endColumn" : 35,
                            "offset" : 4385,
                            "length" : 34
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "Some",
                        "value" : {
                          "type" : "Name",
                          "name" : ["ErrorLibrary", "MutExError"],
                          "pos" : {
                            "type" : "Some",
                            "value" : {
                              "type" : "Position",
                              "uriOpt" : {
                                "type" : "Some",
                                "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                              },
                              "beginLine" : 110,
                              "beginColumn" : 1,
                              "endLine" : 110,
                              "endColumn" : 43,
                              "offset" : 4342,
                              "length" : 42
                            }
                          }
                        }
                      }
                    },
                    {
                      "type" : "ErrorTypeDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "Starvation"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 112,
                            "beginColumn" : 1,
                            "endLine" : 112,
                            "endColumn" : 37,
                            "offset" : 4420,
                            "length" : 36
                          }
                        }
                      },
                      "extendType" : {
                        "type" : "Some",
                        "value" : {
                          "type" : "Name",
                          "name" : ["ErrorLibrary", "MutExError"],
                          "pos" : {
                            "type" : "Some",
                            "value" : {
                              "type" : "Position",
                              "uriOpt" : {
                                "type" : "Some",
                                "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                              },
                              "beginLine" : 110,
                              "beginColumn" : 1,
                              "endLine" : 110,
                              "endColumn" : 43,
                              "offset" : 4342,
                              "length" : 42
                            }
                          }
                        }
                      }
                    }
                  ],
                  "errorTypeSetDef" : [
                    {
                      "type" : "ErrorTypeSetDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "CommonErrors"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 7,
                            "beginColumn" : 1,
                            "endLine" : 7,
                            "endColumn" : 115,
                            "offset" : 77,
                            "length" : 114
                          }
                        }
                      },
                      "errorTypes" : [
                        {
                          "type" : "Name",
                          "name" : ["ErrorLibrary", "ServiceError"],
                          "pos" : {
                            "type" : "Some",
                            "value" : {
                              "type" : "Position",
                              "uriOpt" : {
                                "type" : "Some",
                                "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                              },
                              "beginLine" : 9,
                              "beginColumn" : 1,
                              "endLine" : 9,
                              "endColumn" : 20,
                              "offset" : 217,
                              "length" : 19
                            }
                          }
                        },
                        {
                          "type" : "Name",
                          "name" : ["ErrorLibrary", "TimingRelatedError"],
                          "pos" : {
                            "type" : "Some",
                            "value" : {
                              "type" : "Position",
                              "uriOpt" : {
                                "type" : "Some",
                                "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                              },
                              "beginLine" : 24,
                              "beginColumn" : 1,
                              "endLine" : 24,
                              "endColumn" : 89,
                              "offset" : 858,
                              "length" : 88
                            }
                          }
                        },
                        {
                          "type" : "Name",
                          "name" : ["ErrorLibrary", "ValueRelatedError"],
                          "pos" : {
                            "type" : "Some",
                            "value" : {
                              "type" : "Position",
                              "uriOpt" : {
                                "type" : "Some",
                                "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                              },
                              "beginLine" : 47,
                              "beginColumn" : 1,
                              "endLine" : 47,
                              "endColumn" : 85,
                              "offset" : 1690,
                              "length" : 84
                            }
                          }
                        },
                        {
                          "type" : "Name",
                          "name" : ["ErrorLibrary", "ReplicationError"],
                          "pos" : {
                            "type" : "Some",
                            "value" : {
                              "type" : "Position",
                              "uriOpt" : {
                                "type" : "Some",
                                "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                              },
                              "beginLine" : 78,
                              "beginColumn" : 1,
                              "endLine" : 78,
                              "endColumn" : 24,
                              "offset" : 2866,
                              "length" : 23
                            }
                          }
                        },
                        {
                          "type" : "Name",
                          "name" : ["ErrorLibrary", "ConcurrencyError"],
                          "pos" : {
                            "type" : "Some",
                            "value" : {
                              "type" : "Position",
                              "uriOpt" : {
                                "type" : "Some",
                                "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                              },
                              "beginLine" : 106,
                              "beginColumn" : 1,
                              "endLine" : 106,
                              "endColumn" : 24,
                              "offset" : 4185,
                              "length" : 23
                            }
                          }
                        }
                      ]
                    },
                    {
                      "type" : "ErrorTypeSetDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "TimingRelatedError"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 24,
                            "beginColumn" : 1,
                            "endLine" : 24,
                            "endColumn" : 89,
                            "offset" : 858,
                            "length" : 88
                          }
                        }
                      },
                      "errorTypes" : [
                        {
                          "type" : "Name",
                          "name" : ["ErrorLibrary", "ItemTimingError"],
                          "pos" : {
                            "type" : "Some",
                            "value" : {
                              "type" : "Position",
                              "uriOpt" : {
                                "type" : "Some",
                                "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                              },
                              "beginLine" : 26,
                              "beginColumn" : 1,
                              "endLine" : 26,
                              "endColumn" : 23,
                              "offset" : 969,
                              "length" : 22
                            }
                          }
                        },
                        {
                          "type" : "Name",
                          "name" : ["ErrorLibrary", "SequenceTimingError"],
                          "pos" : {
                            "type" : "Some",
                            "value" : {
                              "type" : "Position",
                              "uriOpt" : {
                                "type" : "Some",
                                "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                              },
                              "beginLine" : 30,
                              "beginColumn" : 1,
                              "endLine" : 30,
                              "endColumn" : 27,
                              "offset" : 1114,
                              "length" : 26
                            }
                          }
                        },
                        {
                          "type" : "Name",
                          "name" : ["ErrorLibrary", "ServiceTimingError"],
                          "pos" : {
                            "type" : "Some",
                            "value" : {
                              "type" : "Position",
                              "uriOpt" : {
                                "type" : "Some",
                                "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                              },
                              "beginLine" : 35,
                              "beginColumn" : 1,
                              "endLine" : 35,
                              "endColumn" : 26,
                              "offset" : 1300,
                              "length" : 25
                            }
                          }
                        }
                      ]
                    },
                    {
                      "type" : "ErrorTypeSetDef",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "ValueRelatedError"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 47,
                            "beginColumn" : 1,
                            "endLine" : 47,
                            "endColumn" : 85,
                            "offset" : 1690,
                            "length" : 84
                          }
                        }
                      },
                      "errorTypes" : [
                        {
                          "type" : "Name",
                          "name" : ["ErrorLibrary", "ItemValueError"],
                          "pos" : {
                            "type" : "Some",
                            "value" : {
                              "type" : "Position",
                              "uriOpt" : {
                                "type" : "Some",
                                "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                              },
                              "beginLine" : 49,
                              "beginColumn" : 1,
                              "endLine" : 49,
                              "endColumn" : 22,
                              "offset" : 1796,
                              "length" : 21
                            }
                          }
                        },
                        {
                          "type" : "Name",
                          "name" : ["ErrorLibrary", "SequenceValueError"],
                          "pos" : {
                            "type" : "Some",
                            "value" : {
                              "type" : "Position",
                              "uriOpt" : {
                                "type" : "Some",
                                "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                              },
                              "beginLine" : 57,
                              "beginColumn" : 1,
                              "endLine" : 57,
                              "endColumn" : 26,
                              "offset" : 2113,
                              "length" : 25
                            }
                          }
                        },
                        {
                          "type" : "Name",
                          "name" : ["ErrorLibrary", "ServiceValueError"],
                          "pos" : {
                            "type" : "Some",
                            "value" : {
                              "type" : "Position",
                              "uriOpt" : {
                                "type" : "Some",
                                "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                              },
                              "beginLine" : 62,
                              "beginColumn" : 1,
                              "endLine" : 62,
                              "endColumn" : 25,
                              "offset" : 2271,
                              "length" : 24
                            }
                          }
                        }
                      ]
                    }
                  ],
                  "alias" : [
                    {
                      "type" : "ErrorAliasDef",
                      "errorType" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "TimingError"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 40,
                            "beginColumn" : 1,
                            "endLine" : 40,
                            "endColumn" : 42,
                            "offset" : 1455,
                            "length" : 41
                          }
                        }
                      },
                      "aliseType" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "ItemTimingError"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 26,
                            "beginColumn" : 1,
                            "endLine" : 26,
                            "endColumn" : 23,
                            "offset" : 969,
                            "length" : 22
                          }
                        }
                      }
                    },
                    {
                      "type" : "ErrorAliasDef",
                      "errorType" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "RateError"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 41,
                            "beginColumn" : 1,
                            "endLine" : 41,
                            "endColumn" : 44,
                            "offset" : 1507,
                            "length" : 43
                          }
                        }
                      },
                      "aliseType" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "SequenceTimingError"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 30,
                            "beginColumn" : 1,
                            "endLine" : 30,
                            "endColumn" : 27,
                            "offset" : 1114,
                            "length" : 26
                          }
                        }
                      }
                    },
                    {
                      "type" : "ErrorAliasDef",
                      "errorType" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "EarlyData"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 42,
                            "beginColumn" : 1,
                            "endLine" : 42,
                            "endColumn" : 33,
                            "offset" : 1551,
                            "length" : 32
                          }
                        }
                      },
                      "aliseType" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "HighRate"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 31,
                            "beginColumn" : 1,
                            "endLine" : 31,
                            "endColumn" : 44,
                            "offset" : 1142,
                            "length" : 43
                          }
                        }
                      }
                    },
                    {
                      "type" : "ErrorAliasDef",
                      "errorType" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "LateData"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 43,
                            "beginColumn" : 1,
                            "endLine" : 43,
                            "endColumn" : 31,
                            "offset" : 1584,
                            "length" : 30
                          }
                        }
                      },
                      "aliseType" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "LowRate"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 32,
                            "beginColumn" : 1,
                            "endLine" : 32,
                            "endColumn" : 43,
                            "offset" : 1187,
                            "length" : 42
                          }
                        }
                      }
                    },
                    {
                      "type" : "ErrorAliasDef",
                      "errorType" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "ServiceTimeShift"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 44,
                            "beginColumn" : 1,
                            "endLine" : 44,
                            "endColumn" : 50,
                            "offset" : 1615,
                            "length" : 49
                          }
                        }
                      },
                      "aliseType" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "ServiceTimingError"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 35,
                            "beginColumn" : 1,
                            "endLine" : 35,
                            "endColumn" : 26,
                            "offset" : 1300,
                            "length" : 25
                          }
                        }
                      }
                    },
                    {
                      "type" : "ErrorAliasDef",
                      "errorType" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "ValueError"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 66,
                            "beginColumn" : 1,
                            "endLine" : 66,
                            "endColumn" : 40,
                            "offset" : 2391,
                            "length" : 39
                          }
                        }
                      },
                      "aliseType" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "ItemValueError"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 49,
                            "beginColumn" : 1,
                            "endLine" : 49,
                            "endColumn" : 22,
                            "offset" : 1796,
                            "length" : 21
                          }
                        }
                      }
                    },
                    {
                      "type" : "ErrorAliasDef",
                      "errorType" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "IncorrectValue"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 67,
                            "beginColumn" : 1,
                            "endLine" : 67,
                            "endColumn" : 44,
                            "offset" : 2431,
                            "length" : 43
                          }
                        }
                      },
                      "aliseType" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "ItemValueError"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 49,
                            "beginColumn" : 1,
                            "endLine" : 49,
                            "endColumn" : 22,
                            "offset" : 1796,
                            "length" : 21
                          }
                        }
                      }
                    },
                    {
                      "type" : "ErrorAliasDef",
                      "errorType" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "ValueCorruption"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 68,
                            "beginColumn" : 1,
                            "endLine" : 68,
                            "endColumn" : 45,
                            "offset" : 2475,
                            "length" : 44
                          }
                        }
                      },
                      "aliseType" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "ItemValueError"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 49,
                            "beginColumn" : 1,
                            "endLine" : 49,
                            "endColumn" : 22,
                            "offset" : 1796,
                            "length" : 21
                          }
                        }
                      }
                    },
                    {
                      "type" : "ErrorAliasDef",
                      "errorType" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "BadValue"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 69,
                            "beginColumn" : 1,
                            "endLine" : 69,
                            "endColumn" : 38,
                            "offset" : 2520,
                            "length" : 37
                          }
                        }
                      },
                      "aliseType" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "ItemValueError"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 49,
                            "beginColumn" : 1,
                            "endLine" : 49,
                            "endColumn" : 22,
                            "offset" : 1796,
                            "length" : 21
                          }
                        }
                      }
                    },
                    {
                      "type" : "ErrorAliasDef",
                      "errorType" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "SequenceError"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 70,
                            "beginColumn" : 1,
                            "endLine" : 70,
                            "endColumn" : 47,
                            "offset" : 2558,
                            "length" : 46
                          }
                        }
                      },
                      "aliseType" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "SequenceValueError"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 57,
                            "beginColumn" : 1,
                            "endLine" : 57,
                            "endColumn" : 26,
                            "offset" : 2113,
                            "length" : 25
                          }
                        }
                      }
                    },
                    {
                      "type" : "ErrorAliasDef",
                      "errorType" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "SubtleValueError"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 72,
                            "beginColumn" : 1,
                            "endLine" : 72,
                            "endColumn" : 54,
                            "offset" : 2606,
                            "length" : 53
                          }
                        }
                      },
                      "aliseType" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "UndetectableValueError"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 50,
                            "beginColumn" : 1,
                            "endLine" : 50,
                            "endColumn" : 53,
                            "offset" : 1819,
                            "length" : 52
                          }
                        }
                      }
                    },
                    {
                      "type" : "ErrorAliasDef",
                      "errorType" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "BenignValueError"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 73,
                            "beginColumn" : 1,
                            "endLine" : 73,
                            "endColumn" : 52,
                            "offset" : 2660,
                            "length" : 51
                          }
                        }
                      },
                      "aliseType" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "DetectableValueError"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 51,
                            "beginColumn" : 1,
                            "endLine" : 51,
                            "endColumn" : 51,
                            "offset" : 1873,
                            "length" : 50
                          }
                        }
                      }
                    },
                    {
                      "type" : "ErrorAliasDef",
                      "errorType" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "SubtleValueCorruption"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 74,
                            "beginColumn" : 1,
                            "endLine" : 74,
                            "endColumn" : 57,
                            "offset" : 2712,
                            "length" : 56
                          }
                        }
                      },
                      "aliseType" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "DetectableValueError"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 51,
                            "beginColumn" : 1,
                            "endLine" : 51,
                            "endColumn" : 51,
                            "offset" : 1873,
                            "length" : 50
                          }
                        }
                      }
                    },
                    {
                      "type" : "ErrorAliasDef",
                      "errorType" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "InconsistentValue"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 98,
                            "beginColumn" : 1,
                            "endLine" : 98,
                            "endColumn" : 48,
                            "offset" : 3828,
                            "length" : 47
                          }
                        }
                      },
                      "aliseType" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "AsymmetricValue"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 80,
                            "beginColumn" : 1,
                            "endLine" : 80,
                            "endColumn" : 57,
                            "offset" : 2948,
                            "length" : 56
                          }
                        }
                      }
                    },
                    {
                      "type" : "ErrorAliasDef",
                      "errorType" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "InconsistentTiming"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 99,
                            "beginColumn" : 1,
                            "endLine" : 99,
                            "endColumn" : 50,
                            "offset" : 3876,
                            "length" : 49
                          }
                        }
                      },
                      "aliseType" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "AsymmetricTiming"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 83,
                            "beginColumn" : 1,
                            "endLine" : 83,
                            "endColumn" : 58,
                            "offset" : 3116,
                            "length" : 57
                          }
                        }
                      }
                    },
                    {
                      "type" : "ErrorAliasDef",
                      "errorType" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "InconsistentOmission"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 100,
                            "beginColumn" : 1,
                            "endLine" : 100,
                            "endColumn" : 54,
                            "offset" : 3926,
                            "length" : 53
                          }
                        }
                      },
                      "aliseType" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "AsymmetricOmission"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 84,
                            "beginColumn" : 1,
                            "endLine" : 84,
                            "endColumn" : 60,
                            "offset" : 3175,
                            "length" : 59
                          }
                        }
                      }
                    },
                    {
                      "type" : "ErrorAliasDef",
                      "errorType" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "InconsistentItemOmission"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 101,
                            "beginColumn" : 1,
                            "endLine" : 101,
                            "endColumn" : 62,
                            "offset" : 3980,
                            "length" : 61
                          }
                        }
                      },
                      "aliseType" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "AsymmetricItemOmission"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 85,
                            "beginColumn" : 1,
                            "endLine" : 85,
                            "endColumn" : 57,
                            "offset" : 3235,
                            "length" : 56
                          }
                        }
                      }
                    },
                    {
                      "type" : "ErrorAliasDef",
                      "errorType" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "InconsistentServiceOmission"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 102,
                            "beginColumn" : 1,
                            "endLine" : 102,
                            "endColumn" : 68,
                            "offset" : 4042,
                            "length" : 67
                          }
                        }
                      },
                      "aliseType" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "AsymmetricServiceOmission"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 86,
                            "beginColumn" : 1,
                            "endLine" : 86,
                            "endColumn" : 60,
                            "offset" : 3292,
                            "length" : 59
                          }
                        }
                      }
                    },
                    {
                      "type" : "ErrorAliasDef",
                      "errorType" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "AsymmetricTransmissive"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 103,
                            "beginColumn" : 1,
                            "endLine" : 103,
                            "endColumn" : 53,
                            "offset" : 4110,
                            "length" : 52
                          }
                        }
                      },
                      "aliseType" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "AsymmetricValue"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 80,
                            "beginColumn" : 1,
                            "endLine" : 80,
                            "endColumn" : 57,
                            "offset" : 2948,
                            "length" : 56
                          }
                        }
                      }
                    }
                  ],
                  "behaveStateMachine" : [
                    {
                      "type" : "BehaveStateMachine",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "FailStop"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 119,
                            "beginColumn" : 1,
                            "endLine" : 127,
                            "endColumn" : 15,
                            "offset" : 4559,
                            "length" : 199
                          }
                        }
                      },
                      "events" : [
                        {
                          "type" : "ErrorEvent",
                          "id" : {
                            "type" : "Name",
                            "name" : ["ErrorLibrary", "FailStop", "Failure"],
                            "pos" : {
                              "type" : "Some",
                              "value" : {
                                "type" : "Position",
                                "uriOpt" : {
                                  "type" : "Some",
                                  "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                                },
                                "beginLine" : 121,
                                "beginColumn" : 2,
                                "endLine" : 121,
                                "endColumn" : 25,
                                "offset" : 4591,
                                "length" : 23
                              }
                            }
                          }
                        }
                      ],
                      "states" : [
                        {
                          "type" : "ErrorState",
                          "id" : {
                            "type" : "Name",
                            "name" : ["ErrorLibrary", "FailStop", "Operational"],
                            "pos" : {
                              "type" : "Some",
                              "value" : {
                                "type" : "Position",
                                "uriOpt" : {
                                  "type" : "Some",
                                  "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                                },
                                "beginLine" : 123,
                                "beginColumn" : 2,
                                "endLine" : 123,
                                "endColumn" : 31,
                                "offset" : 4623,
                                "length" : 29
                              }
                            }
                          },
                          "isInitial" : true
                        },
                        {
                          "type" : "ErrorState",
                          "id" : {
                            "type" : "Name",
                            "name" : ["ErrorLibrary", "FailStop", "FailStop"],
                            "pos" : {
                              "type" : "Some",
                              "value" : {
                                "type" : "Position",
                                "uriOpt" : {
                                  "type" : "Some",
                                  "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                                },
                                "beginLine" : 124,
                                "beginColumn" : 2,
                                "endLine" : 124,
                                "endColumn" : 20,
                                "offset" : 4654,
                                "length" : 18
                              }
                            }
                          },
                          "isInitial" : false
                        }
                      ],
                      "transitions" : [
                        {
                          "type" : "ErrorTransition",
                          "id" : {
                            "type" : "None"
                          },
                          "sourceState" : {
                            "type" : "Name",
                            "name" : ["ErrorLibrary", "FailStop", "Operational"],
                            "pos" : {
                              "type" : "Some",
                              "value" : {
                                "type" : "Position",
                                "uriOpt" : {
                                  "type" : "Some",
                                  "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                                },
                                "beginLine" : 123,
                                "beginColumn" : 2,
                                "endLine" : 123,
                                "endColumn" : 31,
                                "offset" : 4623,
                                "length" : 29
                              }
                            }
                          },
                          "condition" : {
                            "type" : "ConditionTrigger",
                            "events" : [
                              {
                                "type" : "Name",
                                "name" : ["ErrorLibrary", "FailStop", "Failure"],
                                "pos" : {
                                  "type" : "Some",
                                  "value" : {
                                    "type" : "Position",
                                    "uriOpt" : {
                                      "type" : "Some",
                                      "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                                    },
                                    "beginLine" : 121,
                                    "beginColumn" : 2,
                                    "endLine" : 121,
                                    "endColumn" : 25,
                                    "offset" : 4591,
                                    "length" : 23
                                  }
                                }
                              }
                            ],
                            "propagationPoints" : [
                            ]
                          },
                          "targetState" : {
                            "type" : "Name",
                            "name" : ["ErrorLibrary", "FailStop", "FailStop"],
                            "pos" : {
                              "type" : "Some",
                              "value" : {
                                "type" : "Position",
                                "uriOpt" : {
                                  "type" : "Some",
                                  "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                                },
                                "beginLine" : 124,
                                "beginColumn" : 2,
                                "endLine" : 124,
                                "endColumn" : 20,
                                "offset" : 4654,
                                "length" : 18
                              }
                            }
                          }
                        }
                      ],
                      "properties" : [
                      ]
                    },
                    {
                      "type" : "BehaveStateMachine",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "DegradedFailStop"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 130,
                            "beginColumn" : 1,
                            "endLine" : 140,
                            "endColumn" : 15,
                            "offset" : 4813,
                            "length" : 272
                          }
                        }
                      },
                      "events" : [
                        {
                          "type" : "ErrorEvent",
                          "id" : {
                            "type" : "Name",
                            "name" : ["ErrorLibrary", "DegradedFailStop", "Failure"],
                            "pos" : {
                              "type" : "Some",
                              "value" : {
                                "type" : "Position",
                                "uriOpt" : {
                                  "type" : "Some",
                                  "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                                },
                                "beginLine" : 132,
                                "beginColumn" : 2,
                                "endLine" : 132,
                                "endColumn" : 25,
                                "offset" : 4853,
                                "length" : 23
                              }
                            }
                          }
                        }
                      ],
                      "states" : [
                        {
                          "type" : "ErrorState",
                          "id" : {
                            "type" : "Name",
                            "name" : ["ErrorLibrary", "DegradedFailStop", "Operational"],
                            "pos" : {
                              "type" : "Some",
                              "value" : {
                                "type" : "Position",
                                "uriOpt" : {
                                  "type" : "Some",
                                  "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                                },
                                "beginLine" : 134,
                                "beginColumn" : 2,
                                "endLine" : 134,
                                "endColumn" : 31,
                                "offset" : 4885,
                                "length" : 29
                              }
                            }
                          },
                          "isInitial" : true
                        },
                        {
                          "type" : "ErrorState",
                          "id" : {
                            "type" : "Name",
                            "name" : ["ErrorLibrary", "DegradedFailStop", "Degraded"],
                            "pos" : {
                              "type" : "Some",
                              "value" : {
                                "type" : "Position",
                                "uriOpt" : {
                                  "type" : "Some",
                                  "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                                },
                                "beginLine" : 135,
                                "beginColumn" : 2,
                                "endLine" : 135,
                                "endColumn" : 18,
                                "offset" : 4916,
                                "length" : 16
                              }
                            }
                          },
                          "isInitial" : false
                        },
                        {
                          "type" : "ErrorState",
                          "id" : {
                            "type" : "Name",
                            "name" : ["ErrorLibrary", "DegradedFailStop", "FailStop"],
                            "pos" : {
                              "type" : "Some",
                              "value" : {
                                "type" : "Position",
                                "uriOpt" : {
                                  "type" : "Some",
                                  "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                                },
                                "beginLine" : 136,
                                "beginColumn" : 2,
                                "endLine" : 136,
                                "endColumn" : 20,
                                "offset" : 4934,
                                "length" : 18
                              }
                            }
                          },
                          "isInitial" : false
                        }
                      ],
                      "transitions" : [
                        {
                          "type" : "ErrorTransition",
                          "id" : {
                            "type" : "None"
                          },
                          "sourceState" : {
                            "type" : "Name",
                            "name" : ["ErrorLibrary", "DegradedFailStop", "Operational"],
                            "pos" : {
                              "type" : "Some",
                              "value" : {
                                "type" : "Position",
                                "uriOpt" : {
                                  "type" : "Some",
                                  "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                                },
                                "beginLine" : 134,
                                "beginColumn" : 2,
                                "endLine" : 134,
                                "endColumn" : 31,
                                "offset" : 4885,
                                "length" : 29
                              }
                            }
                          },
                          "condition" : {
                            "type" : "ConditionTrigger",
                            "events" : [
                              {
                                "type" : "Name",
                                "name" : ["ErrorLibrary", "DegradedFailStop", "Failure"],
                                "pos" : {
                                  "type" : "Some",
                                  "value" : {
                                    "type" : "Position",
                                    "uriOpt" : {
                                      "type" : "Some",
                                      "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                                    },
                                    "beginLine" : 132,
                                    "beginColumn" : 2,
                                    "endLine" : 132,
                                    "endColumn" : 25,
                                    "offset" : 4853,
                                    "length" : 23
                                  }
                                }
                              }
                            ],
                            "propagationPoints" : [
                            ]
                          },
                          "targetState" : {
                            "type" : "Name",
                            "name" : ["ErrorLibrary", "DegradedFailStop", "Degraded"],
                            "pos" : {
                              "type" : "Some",
                              "value" : {
                                "type" : "Position",
                                "uriOpt" : {
                                  "type" : "Some",
                                  "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                                },
                                "beginLine" : 135,
                                "beginColumn" : 2,
                                "endLine" : 135,
                                "endColumn" : 18,
                                "offset" : 4916,
                                "length" : 16
                              }
                            }
                          }
                        },
                        {
                          "type" : "ErrorTransition",
                          "id" : {
                            "type" : "None"
                          },
                          "sourceState" : {
                            "type" : "Name",
                            "name" : ["ErrorLibrary", "DegradedFailStop", "Degraded"],
                            "pos" : {
                              "type" : "Some",
                              "value" : {
                                "type" : "Position",
                                "uriOpt" : {
                                  "type" : "Some",
                                  "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                                },
                                "beginLine" : 135,
                                "beginColumn" : 2,
                                "endLine" : 135,
                                "endColumn" : 18,
                                "offset" : 4916,
                                "length" : 16
                              }
                            }
                          },
                          "condition" : {
                            "type" : "ConditionTrigger",
                            "events" : [
                              {
                                "type" : "Name",
                                "name" : ["ErrorLibrary", "DegradedFailStop", "Failure"],
                                "pos" : {
                                  "type" : "Some",
                                  "value" : {
                                    "type" : "Position",
                                    "uriOpt" : {
                                      "type" : "Some",
                                      "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                                    },
                                    "beginLine" : 132,
                                    "beginColumn" : 2,
                                    "endLine" : 132,
                                    "endColumn" : 25,
                                    "offset" : 4853,
                                    "length" : 23
                                  }
                                }
                              }
                            ],
                            "propagationPoints" : [
                            ]
                          },
                          "targetState" : {
                            "type" : "Name",
                            "name" : ["ErrorLibrary", "DegradedFailStop", "FailStop"],
                            "pos" : {
                              "type" : "Some",
                              "value" : {
                                "type" : "Position",
                                "uriOpt" : {
                                  "type" : "Some",
                                  "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                                },
                                "beginLine" : 136,
                                "beginColumn" : 2,
                                "endLine" : 136,
                                "endColumn" : 20,
                                "offset" : 4934,
                                "length" : 18
                              }
                            }
                          }
                        }
                      ],
                      "properties" : [
                      ]
                    },
                    {
                      "type" : "BehaveStateMachine",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "FailAndRecover"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 143,
                            "beginColumn" : 1,
                            "endLine" : 153,
                            "endColumn" : 14,
                            "offset" : 5140,
                            "length" : 270
                          }
                        }
                      },
                      "events" : [
                        {
                          "type" : "ErrorEvent",
                          "id" : {
                            "type" : "Name",
                            "name" : ["ErrorLibrary", "FailAndRecover", "Failure"],
                            "pos" : {
                              "type" : "Some",
                              "value" : {
                                "type" : "Position",
                                "uriOpt" : {
                                  "type" : "Some",
                                  "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                                },
                                "beginLine" : 145,
                                "beginColumn" : 2,
                                "endLine" : 145,
                                "endColumn" : 24,
                                "offset" : 5178,
                                "length" : 22
                              }
                            }
                          }
                        },
                        {
                          "type" : "ErrorEvent",
                          "id" : {
                            "type" : "Name",
                            "name" : ["ErrorLibrary", "FailAndRecover", "Recovery"],
                            "pos" : {
                              "type" : "Some",
                              "value" : {
                                "type" : "Position",
                                "uriOpt" : {
                                  "type" : "Some",
                                  "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                                },
                                "beginLine" : 146,
                                "beginColumn" : 2,
                                "endLine" : 146,
                                "endColumn" : 26,
                                "offset" : 5202,
                                "length" : 24
                              }
                            }
                          }
                        }
                      ],
                      "states" : [
                        {
                          "type" : "ErrorState",
                          "id" : {
                            "type" : "Name",
                            "name" : ["ErrorLibrary", "FailAndRecover", "Operational"],
                            "pos" : {
                              "type" : "Some",
                              "value" : {
                                "type" : "Position",
                                "uriOpt" : {
                                  "type" : "Some",
                                  "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                                },
                                "beginLine" : 148,
                                "beginColumn" : 2,
                                "endLine" : 148,
                                "endColumn" : 29,
                                "offset" : 5235,
                                "length" : 27
                              }
                            }
                          },
                          "isInitial" : true
                        },
                        {
                          "type" : "ErrorState",
                          "id" : {
                            "type" : "Name",
                            "name" : ["ErrorLibrary", "FailAndRecover", "Failed"],
                            "pos" : {
                              "type" : "Some",
                              "value" : {
                                "type" : "Position",
                                "uriOpt" : {
                                  "type" : "Some",
                                  "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                                },
                                "beginLine" : 149,
                                "beginColumn" : 2,
                                "endLine" : 149,
                                "endColumn" : 17,
                                "offset" : 5264,
                                "length" : 15
                              }
                            }
                          },
                          "isInitial" : false
                        }
                      ],
                      "transitions" : [
                        {
                          "type" : "ErrorTransition",
                          "id" : {
                            "type" : "None"
                          },
                          "sourceState" : {
                            "type" : "Name",
                            "name" : ["ErrorLibrary", "FailAndRecover", "Operational"],
                            "pos" : {
                              "type" : "Some",
                              "value" : {
                                "type" : "Position",
                                "uriOpt" : {
                                  "type" : "Some",
                                  "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                                },
                                "beginLine" : 148,
                                "beginColumn" : 2,
                                "endLine" : 148,
                                "endColumn" : 29,
                                "offset" : 5235,
                                "length" : 27
                              }
                            }
                          },
                          "condition" : {
                            "type" : "ConditionTrigger",
                            "events" : [
                              {
                                "type" : "Name",
                                "name" : ["ErrorLibrary", "FailAndRecover", "Failure"],
                                "pos" : {
                                  "type" : "Some",
                                  "value" : {
                                    "type" : "Position",
                                    "uriOpt" : {
                                      "type" : "Some",
                                      "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                                    },
                                    "beginLine" : 145,
                                    "beginColumn" : 2,
                                    "endLine" : 145,
                                    "endColumn" : 24,
                                    "offset" : 5178,
                                    "length" : 22
                                  }
                                }
                              }
                            ],
                            "propagationPoints" : [
                            ]
                          },
                          "targetState" : {
                            "type" : "Name",
                            "name" : ["ErrorLibrary", "FailAndRecover", "Failed"],
                            "pos" : {
                              "type" : "Some",
                              "value" : {
                                "type" : "Position",
                                "uriOpt" : {
                                  "type" : "Some",
                                  "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                                },
                                "beginLine" : 149,
                                "beginColumn" : 2,
                                "endLine" : 149,
                                "endColumn" : 17,
                                "offset" : 5264,
                                "length" : 15
                              }
                            }
                          }
                        },
                        {
                          "type" : "ErrorTransition",
                          "id" : {
                            "type" : "None"
                          },
                          "sourceState" : {
                            "type" : "Name",
                            "name" : ["ErrorLibrary", "FailAndRecover", "Failed"],
                            "pos" : {
                              "type" : "Some",
                              "value" : {
                                "type" : "Position",
                                "uriOpt" : {
                                  "type" : "Some",
                                  "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                                },
                                "beginLine" : 149,
                                "beginColumn" : 2,
                                "endLine" : 149,
                                "endColumn" : 17,
                                "offset" : 5264,
                                "length" : 15
                              }
                            }
                          },
                          "condition" : {
                            "type" : "ConditionTrigger",
                            "events" : [
                              {
                                "type" : "Name",
                                "name" : ["ErrorLibrary", "FailAndRecover", "Recovery"],
                                "pos" : {
                                  "type" : "Some",
                                  "value" : {
                                    "type" : "Position",
                                    "uriOpt" : {
                                      "type" : "Some",
                                      "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                                    },
                                    "beginLine" : 146,
                                    "beginColumn" : 2,
                                    "endLine" : 146,
                                    "endColumn" : 26,
                                    "offset" : 5202,
                                    "length" : 24
                                  }
                                }
                              }
                            ],
                            "propagationPoints" : [
                            ]
                          },
                          "targetState" : {
                            "type" : "Name",
                            "name" : ["ErrorLibrary", "FailAndRecover", "Operational"],
                            "pos" : {
                              "type" : "Some",
                              "value" : {
                                "type" : "Position",
                                "uriOpt" : {
                                  "type" : "Some",
                                  "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                                },
                                "beginLine" : 148,
                                "beginColumn" : 2,
                                "endLine" : 148,
                                "endColumn" : 29,
                                "offset" : 5235,
                                "length" : 27
                              }
                            }
                          }
                        }
                      ],
                      "properties" : [
                      ]
                    },
                    {
                      "type" : "BehaveStateMachine",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "DegradedRecovery"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 156,
                            "beginColumn" : 1,
                            "endLine" : 168,
                            "endColumn" : 15,
                            "offset" : 5478,
                            "length" : 357
                          }
                        }
                      },
                      "events" : [
                        {
                          "type" : "ErrorEvent",
                          "id" : {
                            "type" : "Name",
                            "name" : ["ErrorLibrary", "DegradedRecovery", "Failure"],
                            "pos" : {
                              "type" : "Some",
                              "value" : {
                                "type" : "Position",
                                "uriOpt" : {
                                  "type" : "Some",
                                  "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                                },
                                "beginLine" : 158,
                                "beginColumn" : 2,
                                "endLine" : 158,
                                "endColumn" : 25,
                                "offset" : 5518,
                                "length" : 23
                              }
                            }
                          }
                        },
                        {
                          "type" : "ErrorEvent",
                          "id" : {
                            "type" : "Name",
                            "name" : ["ErrorLibrary", "DegradedRecovery", "Recovery"],
                            "pos" : {
                              "type" : "Some",
                              "value" : {
                                "type" : "Position",
                                "uriOpt" : {
                                  "type" : "Some",
                                  "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                                },
                                "beginLine" : 159,
                                "beginColumn" : 2,
                                "endLine" : 159,
                                "endColumn" : 26,
                                "offset" : 5543,
                                "length" : 24
                              }
                            }
                          }
                        }
                      ],
                      "states" : [
                        {
                          "type" : "ErrorState",
                          "id" : {
                            "type" : "Name",
                            "name" : ["ErrorLibrary", "DegradedRecovery", "Operational"],
                            "pos" : {
                              "type" : "Some",
                              "value" : {
                                "type" : "Position",
                                "uriOpt" : {
                                  "type" : "Some",
                                  "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                                },
                                "beginLine" : 161,
                                "beginColumn" : 2,
                                "endLine" : 161,
                                "endColumn" : 31,
                                "offset" : 5576,
                                "length" : 29
                              }
                            }
                          },
                          "isInitial" : true
                        },
                        {
                          "type" : "ErrorState",
                          "id" : {
                            "type" : "Name",
                            "name" : ["ErrorLibrary", "DegradedRecovery", "Degraded"],
                            "pos" : {
                              "type" : "Some",
                              "value" : {
                                "type" : "Position",
                                "uriOpt" : {
                                  "type" : "Some",
                                  "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                                },
                                "beginLine" : 162,
                                "beginColumn" : 2,
                                "endLine" : 162,
                                "endColumn" : 18,
                                "offset" : 5607,
                                "length" : 16
                              }
                            }
                          },
                          "isInitial" : false
                        },
                        {
                          "type" : "ErrorState",
                          "id" : {
                            "type" : "Name",
                            "name" : ["ErrorLibrary", "DegradedRecovery", "FailStop"],
                            "pos" : {
                              "type" : "Some",
                              "value" : {
                                "type" : "Position",
                                "uriOpt" : {
                                  "type" : "Some",
                                  "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                                },
                                "beginLine" : 163,
                                "beginColumn" : 2,
                                "endLine" : 163,
                                "endColumn" : 20,
                                "offset" : 5625,
                                "length" : 18
                              }
                            }
                          },
                          "isInitial" : false
                        }
                      ],
                      "transitions" : [
                        {
                          "type" : "ErrorTransition",
                          "id" : {
                            "type" : "None"
                          },
                          "sourceState" : {
                            "type" : "Name",
                            "name" : ["ErrorLibrary", "DegradedRecovery", "Operational"],
                            "pos" : {
                              "type" : "Some",
                              "value" : {
                                "type" : "Position",
                                "uriOpt" : {
                                  "type" : "Some",
                                  "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                                },
                                "beginLine" : 161,
                                "beginColumn" : 2,
                                "endLine" : 161,
                                "endColumn" : 31,
                                "offset" : 5576,
                                "length" : 29
                              }
                            }
                          },
                          "condition" : {
                            "type" : "ConditionTrigger",
                            "events" : [
                              {
                                "type" : "Name",
                                "name" : ["ErrorLibrary", "DegradedRecovery", "Failure"],
                                "pos" : {
                                  "type" : "Some",
                                  "value" : {
                                    "type" : "Position",
                                    "uriOpt" : {
                                      "type" : "Some",
                                      "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                                    },
                                    "beginLine" : 158,
                                    "beginColumn" : 2,
                                    "endLine" : 158,
                                    "endColumn" : 25,
                                    "offset" : 5518,
                                    "length" : 23
                                  }
                                }
                              }
                            ],
                            "propagationPoints" : [
                            ]
                          },
                          "targetState" : {
                            "type" : "Name",
                            "name" : ["ErrorLibrary", "DegradedRecovery", "Degraded"],
                            "pos" : {
                              "type" : "Some",
                              "value" : {
                                "type" : "Position",
                                "uriOpt" : {
                                  "type" : "Some",
                                  "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                                },
                                "beginLine" : 162,
                                "beginColumn" : 2,
                                "endLine" : 162,
                                "endColumn" : 18,
                                "offset" : 5607,
                                "length" : 16
                              }
                            }
                          }
                        },
                        {
                          "type" : "ErrorTransition",
                          "id" : {
                            "type" : "None"
                          },
                          "sourceState" : {
                            "type" : "Name",
                            "name" : ["ErrorLibrary", "DegradedRecovery", "Degraded"],
                            "pos" : {
                              "type" : "Some",
                              "value" : {
                                "type" : "Position",
                                "uriOpt" : {
                                  "type" : "Some",
                                  "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                                },
                                "beginLine" : 162,
                                "beginColumn" : 2,
                                "endLine" : 162,
                                "endColumn" : 18,
                                "offset" : 5607,
                                "length" : 16
                              }
                            }
                          },
                          "condition" : {
                            "type" : "ConditionTrigger",
                            "events" : [
                              {
                                "type" : "Name",
                                "name" : ["ErrorLibrary", "DegradedRecovery", "Recovery"],
                                "pos" : {
                                  "type" : "Some",
                                  "value" : {
                                    "type" : "Position",
                                    "uriOpt" : {
                                      "type" : "Some",
                                      "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                                    },
                                    "beginLine" : 159,
                                    "beginColumn" : 2,
                                    "endLine" : 159,
                                    "endColumn" : 26,
                                    "offset" : 5543,
                                    "length" : 24
                                  }
                                }
                              }
                            ],
                            "propagationPoints" : [
                            ]
                          },
                          "targetState" : {
                            "type" : "Name",
                            "name" : ["ErrorLibrary", "DegradedRecovery", "Operational"],
                            "pos" : {
                              "type" : "Some",
                              "value" : {
                                "type" : "Position",
                                "uriOpt" : {
                                  "type" : "Some",
                                  "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                                },
                                "beginLine" : 161,
                                "beginColumn" : 2,
                                "endLine" : 161,
                                "endColumn" : 31,
                                "offset" : 5576,
                                "length" : 29
                              }
                            }
                          }
                        },
                        {
                          "type" : "ErrorTransition",
                          "id" : {
                            "type" : "None"
                          },
                          "sourceState" : {
                            "type" : "Name",
                            "name" : ["ErrorLibrary", "DegradedRecovery", "Degraded"],
                            "pos" : {
                              "type" : "Some",
                              "value" : {
                                "type" : "Position",
                                "uriOpt" : {
                                  "type" : "Some",
                                  "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                                },
                                "beginLine" : 162,
                                "beginColumn" : 2,
                                "endLine" : 162,
                                "endColumn" : 18,
                                "offset" : 5607,
                                "length" : 16
                              }
                            }
                          },
                          "condition" : {
                            "type" : "ConditionTrigger",
                            "events" : [
                              {
                                "type" : "Name",
                                "name" : ["ErrorLibrary", "DegradedRecovery", "Failure"],
                                "pos" : {
                                  "type" : "Some",
                                  "value" : {
                                    "type" : "Position",
                                    "uriOpt" : {
                                      "type" : "Some",
                                      "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                                    },
                                    "beginLine" : 158,
                                    "beginColumn" : 2,
                                    "endLine" : 158,
                                    "endColumn" : 25,
                                    "offset" : 5518,
                                    "length" : 23
                                  }
                                }
                              }
                            ],
                            "propagationPoints" : [
                            ]
                          },
                          "targetState" : {
                            "type" : "Name",
                            "name" : ["ErrorLibrary", "DegradedRecovery", "FailStop"],
                            "pos" : {
                              "type" : "Some",
                              "value" : {
                                "type" : "Position",
                                "uriOpt" : {
                                  "type" : "Some",
                                  "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                                },
                                "beginLine" : 163,
                                "beginColumn" : 2,
                                "endLine" : 163,
                                "endColumn" : 20,
                                "offset" : 5625,
                                "length" : 18
                              }
                            }
                          }
                        }
                      ],
                      "properties" : [
                      ]
                    },
                    {
                      "type" : "BehaveStateMachine",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "PermanentTransientFailure"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 171,
                            "beginColumn" : 1,
                            "endLine" : 182,
                            "endColumn" : 14,
                            "offset" : 5899,
                            "length" : 395
                          }
                        }
                      },
                      "events" : [
                        {
                          "type" : "ErrorEvent",
                          "id" : {
                            "type" : "Name",
                            "name" : ["ErrorLibrary", "PermanentTransientFailure", "Failure"],
                            "pos" : {
                              "type" : "Some",
                              "value" : {
                                "type" : "Position",
                                "uriOpt" : {
                                  "type" : "Some",
                                  "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                                },
                                "beginLine" : 173,
                                "beginColumn" : 2,
                                "endLine" : 173,
                                "endColumn" : 24,
                                "offset" : 5948,
                                "length" : 22
                              }
                            }
                          }
                        },
                        {
                          "type" : "ErrorEvent",
                          "id" : {
                            "type" : "Name",
                            "name" : ["ErrorLibrary", "PermanentTransientFailure", "Recovery"],
                            "pos" : {
                              "type" : "Some",
                              "value" : {
                                "type" : "Position",
                                "uriOpt" : {
                                  "type" : "Some",
                                  "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                                },
                                "beginLine" : 174,
                                "beginColumn" : 2,
                                "endLine" : 174,
                                "endColumn" : 26,
                                "offset" : 5972,
                                "length" : 24
                              }
                            }
                          }
                        }
                      ],
                      "states" : [
                        {
                          "type" : "ErrorState",
                          "id" : {
                            "type" : "Name",
                            "name" : ["ErrorLibrary", "PermanentTransientFailure", "Operational"],
                            "pos" : {
                              "type" : "Some",
                              "value" : {
                                "type" : "Position",
                                "uriOpt" : {
                                  "type" : "Some",
                                  "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                                },
                                "beginLine" : 176,
                                "beginColumn" : 2,
                                "endLine" : 176,
                                "endColumn" : 29,
                                "offset" : 6005,
                                "length" : 27
                              }
                            }
                          },
                          "isInitial" : true
                        },
                        {
                          "type" : "ErrorState",
                          "id" : {
                            "type" : "Name",
                            "name" : ["ErrorLibrary", "PermanentTransientFailure", "FailedTransient"],
                            "pos" : {
                              "type" : "Some",
                              "value" : {
                                "type" : "Position",
                                "uriOpt" : {
                                  "type" : "Some",
                                  "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                                },
                                "beginLine" : 177,
                                "beginColumn" : 2,
                                "endLine" : 177,
                                "endColumn" : 26,
                                "offset" : 6034,
                                "length" : 24
                              }
                            }
                          },
                          "isInitial" : false
                        },
                        {
                          "type" : "ErrorState",
                          "id" : {
                            "type" : "Name",
                            "name" : ["ErrorLibrary", "PermanentTransientFailure", "FailedPermanent"],
                            "pos" : {
                              "type" : "Some",
                              "value" : {
                                "type" : "Position",
                                "uriOpt" : {
                                  "type" : "Some",
                                  "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                                },
                                "beginLine" : 178,
                                "beginColumn" : 2,
                                "endLine" : 178,
                                "endColumn" : 26,
                                "offset" : 6060,
                                "length" : 24
                              }
                            }
                          },
                          "isInitial" : false
                        }
                      ],
                      "transitions" : [
                        {
                          "type" : "ErrorTransition",
                          "id" : {
                            "type" : "None"
                          },
                          "sourceState" : {
                            "type" : "Name",
                            "name" : ["ErrorLibrary", "PermanentTransientFailure", "Operational"],
                            "pos" : {
                              "type" : "Some",
                              "value" : {
                                "type" : "Position",
                                "uriOpt" : {
                                  "type" : "Some",
                                  "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                                },
                                "beginLine" : 176,
                                "beginColumn" : 2,
                                "endLine" : 176,
                                "endColumn" : 29,
                                "offset" : 6005,
                                "length" : 27
                              }
                            }
                          },
                          "condition" : {
                            "type" : "ConditionTrigger",
                            "events" : [
                              {
                                "type" : "Name",
                                "name" : ["ErrorLibrary", "PermanentTransientFailure", "Failure"],
                                "pos" : {
                                  "type" : "Some",
                                  "value" : {
                                    "type" : "Position",
                                    "uriOpt" : {
                                      "type" : "Some",
                                      "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                                    },
                                    "beginLine" : 173,
                                    "beginColumn" : 2,
                                    "endLine" : 173,
                                    "endColumn" : 24,
                                    "offset" : 5948,
                                    "length" : 22
                                  }
                                }
                              }
                            ],
                            "propagationPoints" : [
                            ]
                          },
                          "targetState" : {
                            "type" : "Name",
                            "name" : ["ErrorLibrary", "PermanentTransientFailure", "FailedTransient"],
                            "pos" : {
                              "type" : "Some",
                              "value" : {
                                "type" : "Position",
                                "uriOpt" : {
                                  "type" : "Some",
                                  "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                                },
                                "beginLine" : 177,
                                "beginColumn" : 2,
                                "endLine" : 177,
                                "endColumn" : 26,
                                "offset" : 6034,
                                "length" : 24
                              }
                            }
                          }
                        },
                        {
                          "type" : "ErrorTransition",
                          "id" : {
                            "type" : "None"
                          },
                          "sourceState" : {
                            "type" : "Name",
                            "name" : ["ErrorLibrary", "PermanentTransientFailure", "FailedTransient"],
                            "pos" : {
                              "type" : "Some",
                              "value" : {
                                "type" : "Position",
                                "uriOpt" : {
                                  "type" : "Some",
                                  "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                                },
                                "beginLine" : 177,
                                "beginColumn" : 2,
                                "endLine" : 177,
                                "endColumn" : 26,
                                "offset" : 6034,
                                "length" : 24
                              }
                            }
                          },
                          "condition" : {
                            "type" : "ConditionTrigger",
                            "events" : [
                              {
                                "type" : "Name",
                                "name" : ["ErrorLibrary", "PermanentTransientFailure", "Recovery"],
                                "pos" : {
                                  "type" : "Some",
                                  "value" : {
                                    "type" : "Position",
                                    "uriOpt" : {
                                      "type" : "Some",
                                      "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                                    },
                                    "beginLine" : 174,
                                    "beginColumn" : 2,
                                    "endLine" : 174,
                                    "endColumn" : 26,
                                    "offset" : 5972,
                                    "length" : 24
                                  }
                                }
                              }
                            ],
                            "propagationPoints" : [
                            ]
                          },
                          "targetState" : {
                            "type" : "Name",
                            "name" : ["ErrorLibrary", "PermanentTransientFailure", "Operational"],
                            "pos" : {
                              "type" : "Some",
                              "value" : {
                                "type" : "Position",
                                "uriOpt" : {
                                  "type" : "Some",
                                  "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                                },
                                "beginLine" : 176,
                                "beginColumn" : 2,
                                "endLine" : 176,
                                "endColumn" : 29,
                                "offset" : 6005,
                                "length" : 27
                              }
                            }
                          }
                        }
                      ],
                      "properties" : [
                      ]
                    },
                    {
                      "type" : "BehaveStateMachine",
                      "id" : {
                        "type" : "Name",
                        "name" : ["ErrorLibrary", "FailRecoveryFailure"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                            },
                            "beginLine" : 186,
                            "beginColumn" : 1,
                            "endLine" : 197,
                            "endColumn" : 14,
                            "offset" : 6374,
                            "length" : 350
                          }
                        }
                      },
                      "events" : [
                        {
                          "type" : "ErrorEvent",
                          "id" : {
                            "type" : "Name",
                            "name" : ["ErrorLibrary", "FailRecoveryFailure", "Failure"],
                            "pos" : {
                              "type" : "Some",
                              "value" : {
                                "type" : "Position",
                                "uriOpt" : {
                                  "type" : "Some",
                                  "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                                },
                                "beginLine" : 188,
                                "beginColumn" : 2,
                                "endLine" : 188,
                                "endColumn" : 24,
                                "offset" : 6417,
                                "length" : 22
                              }
                            }
                          }
                        },
                        {
                          "type" : "ErrorEvent",
                          "id" : {
                            "type" : "Name",
                            "name" : ["ErrorLibrary", "FailRecoveryFailure", "Recovery"],
                            "pos" : {
                              "type" : "Some",
                              "value" : {
                                "type" : "Position",
                                "uriOpt" : {
                                  "type" : "Some",
                                  "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                                },
                                "beginLine" : 189,
                                "beginColumn" : 2,
                                "endLine" : 189,
                                "endColumn" : 26,
                                "offset" : 6441,
                                "length" : 24
                              }
                            }
                          }
                        }
                      ],
                      "states" : [
                        {
                          "type" : "ErrorState",
                          "id" : {
                            "type" : "Name",
                            "name" : ["ErrorLibrary", "FailRecoveryFailure", "Operational"],
                            "pos" : {
                              "type" : "Some",
                              "value" : {
                                "type" : "Position",
                                "uriOpt" : {
                                  "type" : "Some",
                                  "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                                },
                                "beginLine" : 191,
                                "beginColumn" : 2,
                                "endLine" : 191,
                                "endColumn" : 29,
                                "offset" : 6474,
                                "length" : 27
                              }
                            }
                          },
                          "isInitial" : true
                        },
                        {
                          "type" : "ErrorState",
                          "id" : {
                            "type" : "Name",
                            "name" : ["ErrorLibrary", "FailRecoveryFailure", "Failed"],
                            "pos" : {
                              "type" : "Some",
                              "value" : {
                                "type" : "Position",
                                "uriOpt" : {
                                  "type" : "Some",
                                  "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                                },
                                "beginLine" : 192,
                                "beginColumn" : 2,
                                "endLine" : 192,
                                "endColumn" : 17,
                                "offset" : 6503,
                                "length" : 15
                              }
                            }
                          },
                          "isInitial" : false
                        },
                        {
                          "type" : "ErrorState",
                          "id" : {
                            "type" : "Name",
                            "name" : ["ErrorLibrary", "FailRecoveryFailure", "FailStop"],
                            "pos" : {
                              "type" : "Some",
                              "value" : {
                                "type" : "Position",
                                "uriOpt" : {
                                  "type" : "Some",
                                  "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                                },
                                "beginLine" : 193,
                                "beginColumn" : 2,
                                "endLine" : 193,
                                "endColumn" : 18,
                                "offset" : 6520,
                                "length" : 16
                              }
                            }
                          },
                          "isInitial" : false
                        }
                      ],
                      "transitions" : [
                        {
                          "type" : "ErrorTransition",
                          "id" : {
                            "type" : "None"
                          },
                          "sourceState" : {
                            "type" : "Name",
                            "name" : ["ErrorLibrary", "FailRecoveryFailure", "Operational"],
                            "pos" : {
                              "type" : "Some",
                              "value" : {
                                "type" : "Position",
                                "uriOpt" : {
                                  "type" : "Some",
                                  "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                                },
                                "beginLine" : 191,
                                "beginColumn" : 2,
                                "endLine" : 191,
                                "endColumn" : 29,
                                "offset" : 6474,
                                "length" : 27
                              }
                            }
                          },
                          "condition" : {
                            "type" : "ConditionTrigger",
                            "events" : [
                              {
                                "type" : "Name",
                                "name" : ["ErrorLibrary", "FailRecoveryFailure", "Failure"],
                                "pos" : {
                                  "type" : "Some",
                                  "value" : {
                                    "type" : "Position",
                                    "uriOpt" : {
                                      "type" : "Some",
                                      "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                                    },
                                    "beginLine" : 188,
                                    "beginColumn" : 2,
                                    "endLine" : 188,
                                    "endColumn" : 24,
                                    "offset" : 6417,
                                    "length" : 22
                                  }
                                }
                              }
                            ],
                            "propagationPoints" : [
                            ]
                          },
                          "targetState" : {
                            "type" : "Name",
                            "name" : ["ErrorLibrary", "FailRecoveryFailure", "Failed"],
                            "pos" : {
                              "type" : "Some",
                              "value" : {
                                "type" : "Position",
                                "uriOpt" : {
                                  "type" : "Some",
                                  "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                                },
                                "beginLine" : 192,
                                "beginColumn" : 2,
                                "endLine" : 192,
                                "endColumn" : 17,
                                "offset" : 6503,
                                "length" : 15
                              }
                            }
                          }
                        },
                        {
                          "type" : "ErrorTransition",
                          "id" : {
                            "type" : "None"
                          },
                          "sourceState" : {
                            "type" : "Name",
                            "name" : ["ErrorLibrary", "FailRecoveryFailure", "Failed"],
                            "pos" : {
                              "type" : "Some",
                              "value" : {
                                "type" : "Position",
                                "uriOpt" : {
                                  "type" : "Some",
                                  "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                                },
                                "beginLine" : 192,
                                "beginColumn" : 2,
                                "endLine" : 192,
                                "endColumn" : 17,
                                "offset" : 6503,
                                "length" : 15
                              }
                            }
                          },
                          "condition" : {
                            "type" : "ConditionTrigger",
                            "events" : [
                              {
                                "type" : "Name",
                                "name" : ["ErrorLibrary", "FailRecoveryFailure", "Recovery"],
                                "pos" : {
                                  "type" : "Some",
                                  "value" : {
                                    "type" : "Position",
                                    "uriOpt" : {
                                      "type" : "Some",
                                      "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                                    },
                                    "beginLine" : 189,
                                    "beginColumn" : 2,
                                    "endLine" : 189,
                                    "endColumn" : 26,
                                    "offset" : 6441,
                                    "length" : 24
                                  }
                                }
                              }
                            ],
                            "propagationPoints" : [
                            ]
                          },
                          "targetState" : {
                            "type" : "Name",
                            "name" : ["ErrorLibrary", "FailRecoveryFailure", "Operational"],
                            "pos" : {
                              "type" : "Some",
                              "value" : {
                                "type" : "Position",
                                "uriOpt" : {
                                  "type" : "Some",
                                  "value" : "\/org.osate.aadl2.errormodel.contrib\/resources\/packages\/ErrorLibrary.aadl"
                                },
                                "beginLine" : 191,
                                "beginColumn" : 2,
                                "endLine" : 191,
                                "endColumn" : 29,
                                "offset" : 6474,
                                "length" : 27
                              }
                            }
                          }
                        }
                      ],
                      "properties" : [
                      ]
                    }
                  ]
                },
                {
                  "type" : "SmfLibrary",
                  "types" : [
                    {
                      "type" : "SmfType",
                      "typeName" : {
                        "type" : "Name",
                        "name" : ["Low"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/Test\/TestLib.aadl"
                            },
                            "beginLine" : 3,
                            "beginColumn" : 4,
                            "endLine" : 3,
                            "endColumn" : 15,
                            "offset" : 57,
                            "length" : 11
                          }
                        }
                      },
                      "parentType" : {
                        "type" : "None"
                      }
                    },
                    {
                      "type" : "SmfType",
                      "typeName" : {
                        "type" : "Name",
                        "name" : ["High"],
                        "pos" : {
                          "type" : "Some",
                          "value" : {
                            "type" : "Position",
                            "uriOpt" : {
                              "type" : "Some",
                              "value" : "\/Test\/TestLib.aadl"
                            },
                            "beginLine" : 4,
                            "beginColumn" : 4,
                            "endLine" : 4,
                            "endColumn" : 28,
                            "offset" : 72,
                            "length" : 24
                          }
                        }
                      },
                      "parentType" : {
                        "type" : "Some",
                        "value" : {
                          "type" : "Name",
                          "name" : ["Low"],
                          "pos" : {
                            "type" : "Some",
                            "value" : {
                              "type" : "Position",
                              "uriOpt" : {
                                "type" : "Some",
                                "value" : "\/Test\/TestLib.aadl"
                              },
                              "beginLine" : 3,
                              "beginColumn" : 4,
                              "endLine" : 3,
                              "endColumn" : 15,
                              "offset" : 57,
                              "length" : 11
                            }
                          }
                        }
                      }
                    }
                  ]
                }
              ],
              "dataComponents" : [
              ]
            }`;

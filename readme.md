# Setup


### Pre-Requisite
  1. [Sireum](http://logika.sireum.org/doc/01-getting-started/index.html)
  2. [Git (optional)](https://git-scm.com/downloads)

### Installation
Follow [Sireum IVE installation](http://logika.sireum.org/doc/01-getting-started/index.html#installation) to obtain and install Sireum IVE 

Using git to clone the following repository
```bash
git clone -b master git@github.com:sireum/v3.git sireum-v3
git clone git@github.com:sireum/v3-awas.git sireum-v3/awas
```

### Tasks - Awas Language
- [x] Components
- [x] Connections
- [x] Error Types
- [x] State Machines
- [x] Propagation
- [x] Behavior 
- [x] Transitions
- [X] Incremental definition of flows
- [ ] Nested Components 
- [ ] System Name
- [ ] Bus
- [ ] Property Map with each node to support Annex and source language location data

### Tasks - Symbolic Check
- [x] Symbol Table
- [x] Type Table
- [x] Resource and ResourceUri for each Id 
- [ ] Api for extracting component/connection Uri from port or flow Uri
- [ ] Construction of flow, behavior and transition tables

### Tasks - Sanity Check 
- [x] Flow check based on propagation
- [ ] behavior and transition check based on flow and propagation
- [x] Inter-component check

### Tasks - Graph construction
- [x] Awas graph
- [ ] Flow Graph - refactor FptcGraph as flow graph
- [x] Basic Node
- [ ] Split FptcNode into FlowNode and FptcNode

### Tasks - Fptc Analysis 
- [ ] Remove errors 
- [ ] Make it consistent with the current graph api
- [ ] Move it to master branch

### Tasks - Reachability Analysis
- [x] Reachability Node level
- [x] Port level reachability
  - [x] Port successor and predecessor  
- [ ] Converters between port and node level uris
- [ ] Error flow 
- [ ] getCycle and detectCycle on slice rather than entire graph 
  - [ ] Investigate the JgraphT's findCyclesContainingVertex implementation
 

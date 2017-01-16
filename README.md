HES-SO//Master - Deepening project
=====================

#### KlugHDL : a diagram generator for SpinalHDL

Author : Sylvain Julmy <sylvain.julmy@master.hes-so.ch> or <sylvain.julmy@gmail.com>

### Dependencies and setup

## Dependencies
* **Git**
* **Scala**
* **Sbt**
* **Draw2d**, a javascript library (GPL2, already in the project) : <http://www.draw2d.org/draw2d/>
* **Dagre**, a javascript library (MIT, already in the project) : <https://github.com/cpettitt/dagre>
* **Graphlib**, a javascript library used by **Dagre** (MIT) : <https://github.com/cpettitt/graphlib>
* **SpinalHDL**, a Scala library (MIT, added in SBT) : <https://github.com/SpinalHDL/SpinalHDL>
* **LiftJson**, a Scala library (Apache 2.0, added in SBT) : <https://liftweb.net/>

## Setup
```
git clone https://github.com/SnipyJulmy/MSE_1617_PA.git
cd MSE_1617_PA/KlugHDL
sbt
# inside the sbt console
> reload
> update
> compile
> run
```

Now you could open the diagam with your broswer !

```
firefox diagrams/index.html
```


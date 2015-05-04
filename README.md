About
-----
Source code for running the experiments for the paper:

Wojciech Jaśkowski & Marcin Szubert, "Coevolutionary CMA-ES for Knowledge-Free Learning of Game Position Evaluation", Computational Intelligence and AI in Games, IEEE Transactions on, 2015

Authors
-------
Wojciech Jaśkowski
Marcin Szubert

Prerequisites
-------------
Java 8, Maven

Building
--------
```bash
> mvn package
```

Running
-------
Running the tournament on Lucas boards (Table III in the paper):
```bash
> java -Dlog4j.configuration=file:log4j.properties -Dgames_scheme=LUCAS_BOARDS -jar cevo.jar put.ci.cevo.experiments.runs.othello.OthelloPlayersRoundRobinTournament
```

Running the tournament on with randomized opponents (Table IV in the paper):
```bash
> java -Dlog4j.configuration=file:log4j.properties -Dgames_scheme=OPPONENTS_EPS -jar cevo.jar put.ci.cevo.experiments.runs.othello.OthelloPlayersRoundRobinTournament
```

To run the experiments described in the paper (for random seed 123):
```
java -Xmx4g -Dlog4j.configuration=file:log4j.properties -Dresults_dir=results/all-2_200_CMAES/123 -Dseed=123 -Dframework.properties=configs/tciaig-2015-othello/all-4-2x2_400_CMAES.properties -jar cevo.jar put.ci.cevo.experiments.runs.othello.wj_cig2014rework.NTuplesOthelloCoevolutionaryEmperorExperiment
```

Available configs:
* all-2_21_CMAES.properties      
* all-3_23_CMAES.properties
* all-4_26_CMAES.properties
* all-2_200_CMAES.properties      
* all-3_300_CMAES.properties
* all-4_400_CMAES.properties
* all-2_200_ES.properties        
* all-3_300_ES.properties        
* all-4_400_ES.properties        
* all-4-2x2_400_CMAES.properties 
* rand-24x3_300_CMAES.properties
* rand-10x3_200_CMAES.properties
* rand-7x5_400_CMAES.properties
* rand-21x4_400_CMAES.properties
* rand-8x4_300_CMAES.properties

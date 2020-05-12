### Demo
Simple application to set routes for trains. A train has a type (TransEuropean - **TE**, Intercity - **IC**, Regional - **R**) and a schedule (list of stations and hours of arrival)
```puml

Train --|| TrainInfo :kind
Train ---|{ Stop :schedule

TrainInfo <|-- TransEuropean
TrainInfo <|-- Intercity
TrainInfo <|-- Regional

Stop --|| Time
Stop --|| Station
```
### Full schedule example:

| Train | Time | Station |
|:---:|:---:|:---:|
| IC42 | 10:00  | Bucharest |
| IC42 | 14:00  | Budapest |
| IC42 | 22:00 | Amsterdam |
| R100 | 12:00 | Bucharest |
| R100 | 13:30 | Constanta |
| P999 | 9:00 | Bucharest |
| P999 | 9:00 | Airport |

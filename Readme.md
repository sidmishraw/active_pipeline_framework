## Active Pipeline framework
[Using lecture notes from Dr. Pearce](http://www.cs.sjsu.edu/faculty/pearce/modules/projects/pipes/index.htm)

A pipe is a message queue. A message can be anything. A filter is a process, thread, or other component that perpetually reads messages from an input pipe, one at a time, processes each message, then writes the result to an output pipe. Thus, it is possible to form pipelines of filters connected by pipes.

This uses active filters. Each filter has a run method that perpetually reads messages from its input pipe, processes them, then writes the processed message to its output pipe. Instead of a single message, a pipe has a message queue. A filter must wait for data if its input pipe is empty. Pipes must be monitors with synchronized read and write operations.


### Background
#### Filter Classification

There are four types of filters: *producers*, *consumers*, *transformers*, and *testers*. 

* A *producer* is a producer of messages. It has no input pipe. It generates a message into its output pipe. 

* A *consumer* is a consumer of messages. It has no output pipe. It eats messages taken from its input pipe. 

* A *transformer* reads a message from its input pipe, modulates it, then writes the result to its output pipe. (This is what DOS and UNIX programmers call filters.) 

* A *tester* reads a message from its input pipe, then tests it. If the message passes the test, it is written, unaltered, to the output pipe; otherwise, it is discarded. (This is what signal processing engineers call filters).


Filters can also be classified as *active* or *passive*.
 
* An active filter has a control loop that runs in its own process or thread. It perpetually reads messages from its input pipe, processes them, then writes the results to its output pipe. An active filter needs to be derived from a thread class provided by the operating system:

	```java
	class Filter extends Thread { ... }
	```

A passive filter has a control loop function. Here's a simplified version that assumes the filter is a transformer:


	void controlLoop()
	{
	   while(true)
	   {
	      Message val = inPipe.read();
	      val = transform(val); // do something to val
	      outPipe.write(val);
	   }
	}


When activated, a passive filter reads a single message from its input pipe, processes it, then writes the result to its output pipe:

	void activate()
	{
	   Message val = inPipe.read();
	   val = transform(val); // do something to val
	   outPipe.write(val);
	}

There are two types of passive filters. 

* A *data-driven filter* is activated when another filter writes a message into its input pipe. 
* A *demand-driven filter* is activated when another filter attempts to read a message from its empty output pipe.


### Full scope
After completing the framework, use the framework to implement:
* *GarBage Band*: GarBage Band is a digital music studio that processes streams of musical notes. Each note has frequency, amplitude, and duration. (These can be floating point numbers.) GB provides the following configurable filters:

  * Amplifiers: Increases or decreases the volume of each note
  * Players: Play the notes through the computer's sound system.
  * Noise Filters: Remove noise notes from the stream. A noise note can be a note that doesn't make sense: too loud, too quiet, too short, inaudible frequency, or a note that's suspiciously different from its predecessor (this might be static or a pop).
  * Duration Filters: Remove notes that are too long or too short.
  * Digital Composer: Generates a random sequence of <= 100 notes.


Output for GarBage Band when run for producing Notes on a 'Piano':
```
Starting the GarBage Band application... start dancing.. PepePls!
Started Producer: DigitalComposer
Started Transformer: Amplifier
Started Tester: NoiseFilter
Started Tester: DurationFilter
Started Consumer: Player
Composer: Produced note #0 having frequency=595.8281 amplitude=95.101395 duration=857.1747
Composer: Produced note #1 having frequency=373.0859 amplitude=39.381535 duration=731.64154
Composer: Produced note #2 having frequency=793.96136 amplitude=58.393055 duration=193.36432
Amplification: Amplified from old amplitude=95.101395 to new amplitude=85.101395
Composer: Produced note #3 having frequency=32.63432 amplitude=47.474403 duration=517.95953
Amplification: Amplified from old amplitude=39.381535 to new amplitude=29.381535
Composer: Produced note #4 having frequency=820.6652 amplitude=15.8644085 duration=277.59427
Amplification: Amplified from old amplitude=58.393055 to new amplitude=48.393055
Composer: Produced note #5 having frequency=62.850414 amplitude=25.141705 duration=969.9404
Amplification: Amplified from old amplitude=47.474403 to new amplitude=37.474403
Composer: Produced note #6 having frequency=474.4972 amplitude=34.064396 duration=744.38074
Amplification: Amplified from old amplitude=15.8644085 to new amplitude=5.8644085
Composer: Produced note #7 having frequency=227.11378 amplitude=35.309376 duration=821.4445
Amplification: Amplified from old amplitude=25.141705 to new amplitude=15.141705
Composer: Produced note #8 having frequency=68.41666 amplitude=33.650368 duration=70.92327
Amplification: Amplified from old amplitude=34.064396 to new amplitude=24.064396
Composer: Produced note #9 having frequency=810.5415 amplitude=33.093323 duration=799.0312
Amplification: Amplified from old amplitude=35.309376 to new amplitude=25.309376
Composer: Produced note #10 having frequency=921.6863 amplitude=6.000656 duration=295.87555
Amplification: Amplified from old amplitude=33.650368 to new amplitude=23.650368
Composer: Produced note #11 having frequency=674.6071 amplitude=90.57586 duration=427.0139
Amplification: Amplified from old amplitude=33.093323 to new amplitude=23.093323
Composer: Produced note #12 having frequency=978.7508 amplitude=69.65505 duration=184.87334
Composer: Produced note #13 having frequency=714.1097 amplitude=24.495167 duration=637.5646
Composer: Produced note #14 having frequency=222.99557 amplitude=11.84572 duration=992.35394
Composer: Produced note #15 having frequency=763.19824 amplitude=14.191336 duration=220.58415
Composer: Produced note #16 having frequency=746.6022 amplitude=9.440494 duration=312.40976
Amplification: Amplified from old amplitude=6.000656 to new amplitude=-3.9993439
Amplification: Amplified from old amplitude=90.57586 to new amplitude=80.57586
Amplification: Amplified from old amplitude=69.65505 to new amplitude=59.655052
Amplification: Amplified from old amplitude=24.495167 to new amplitude=14.495167
Amplification: Amplified from old amplitude=11.84572 to new amplitude=1.8457203
Amplification: Amplified from old amplitude=14.191336 to new amplitude=4.1913357
Amplification: Amplified from old amplitude=9.440494 to new amplitude=-0.5595064
Composer: Produced note #17 having frequency=579.95166 amplitude=84.68433 duration=218.02205
Composer: Produced note #18 having frequency=604.704 amplitude=18.907791 duration=393.42166
Amplification: Amplified from old amplitude=84.68433 to new amplitude=74.68433
Composer: Produced note #19 having frequency=383.9271 amplitude=4.8173666 duration=788.57135
Amplification: Amplified from old amplitude=18.907791 to new amplitude=8.907791
Composer: Produced note #20 having frequency=247.07866 amplitude=26.092327 duration=82.979324
Amplification: Amplified from old amplitude=4.8173666 to new amplitude=-5.1826334
Composer: Produced note #21 having frequency=945.6244 amplitude=77.68248 duration=314.40765
Amplification: Amplified from old amplitude=26.092327 to new amplitude=16.092327
Composer: Produced note #22 having frequency=851.50165 amplitude=5.241829 duration=772.75903
Amplification: Amplified from old amplitude=77.68248 to new amplitude=67.68248
Composer: Produced note #23 having frequency=963.29034 amplitude=10.153508 duration=213.94122
Amplification: Amplified from old amplitude=5.241829 to new amplitude=-4.758171
Composer: Produced note #24 having frequency=269.2077 amplitude=11.213123 duration=159.9325
Composer: Produced note #25 having frequency=691.21155 amplitude=15.005648 duration=184.1945
Composer: Produced note #26 having frequency=638.0857 amplitude=90.18123 duration=487.23166
Composer: Produced note #27 having frequency=416.32056 amplitude=75.39333 duration=850.9158
Composer: Produced note #28 having frequency=38.836777 amplitude=93.72508 duration=725.43646
Composer: Produced note #29 having frequency=365.55237 amplitude=52.107655 duration=497.75327
Composer: Produced note #30 having frequency=496.19287 amplitude=9.04603 duration=56.089283
Amplification: Amplified from old amplitude=10.153508 to new amplitude=0.15350819
Composer: Produced note #31 having frequency=692.3428 amplitude=29.247368 duration=686.115
Amplification: Amplified from old amplitude=11.213123 to new amplitude=1.2131233
Amplification: Amplified from old amplitude=15.005648 to new amplitude=5.0056477
Amplification: Amplified from old amplitude=90.18123 to new amplitude=80.18123
Composer: Produced note #32 having frequency=829.4982 amplitude=30.568987 duration=590.43585
Amplification: Amplified from old amplitude=75.39333 to new amplitude=65.39333
Composer: Produced note #33 having frequency=656.6849 amplitude=47.770084 duration=2.1500587
Composer: Produced note #34 having frequency=429.51584 amplitude=37.815636 duration=824.35803
Amplification: Amplified from old amplitude=93.72508 to new amplitude=83.72508
Composer: Produced note #35 having frequency=400.2049 amplitude=63.307346 duration=408.40912
Amplification: Amplified from old amplitude=52.107655 to new amplitude=42.107655
Composer: Produced note #36 having frequency=801.8046 amplitude=51.516884 duration=836.0898
Amplification: Amplified from old amplitude=9.04603 to new amplitude=-0.95396996
Composer: Produced note #37 having frequency=776.3235 amplitude=67.57012 duration=681.48035
Amplification: Amplified from old amplitude=29.247368 to new amplitude=19.247368
Amplification: Amplified from old amplitude=30.568987 to new amplitude=20.568987
Composer: Produced note #38 having frequency=622.6718 amplitude=82.813354 duration=140.88803
Amplification: Amplified from old amplitude=47.770084 to new amplitude=37.770084
Composer: Produced note #39 having frequency=663.53094 amplitude=94.65215 duration=559.6151
Amplification: Amplified from old amplitude=37.815636 to new amplitude=27.815636
Amplification: Amplified from old amplitude=63.307346 to new amplitude=53.307346
Amplification: Amplified from old amplitude=51.516884 to new amplitude=41.516884
Amplification: Amplified from old amplitude=67.57012 to new amplitude=57.57012
Amplification: Amplified from old amplitude=82.813354 to new amplitude=72.813354
Amplification: Amplified from old amplitude=94.65215 to new amplitude=84.65215
Composer: Produced note #40 having frequency=612.6513 amplitude=22.495985 duration=859.16675
Composer: Produced note #41 having frequency=707.3706 amplitude=96.40983 duration=753.9628
Composer: Produced note #42 having frequency=961.61957 amplitude=82.48189 duration=873.3244
NoiseFilter: Accepted Note: frequency=595.8281 amplitude=85.101395 duration=857.1747
Amplification: Amplified from old amplitude=22.495985 to new amplitude=12.495985
Amplification: Amplified from old amplitude=96.40983 to new amplitude=86.40983
Amplification: Amplified from old amplitude=82.48189 to new amplitude=72.48189
Composer: Produced note #43 having frequency=857.7434 amplitude=74.34756 duration=640.03455
DurationFilter: Accepted Note: frequency=595.8281 amplitude=85.101395 duration=857.1747
NoiseFilter: Too quiet or too loud: volume=29.381535
Amplification: Amplified from old amplitude=74.34756 to new amplitude=64.34756
Playing: frequency=595.8281 amplitude=85.101395 duration=857.1747
Composer: Produced note #44 having frequency=15.772999 amplitude=1.5193164 duration=570.0631
Composer: Produced note #45 having frequency=357.31317 amplitude=45.14454 duration=775.8356
Amplification: Amplified from old amplitude=1.5193164 to new amplitude=-8.480683
NoiseFilter: Accepted Note: frequency=793.96136 amplitude=48.393055 duration=193.36432
Amplification: Amplified from old amplitude=45.14454 to new amplitude=35.14454
DurationFilter: Too short of a duration: duration=193.36432
Composer: Produced note #46 having frequency=807.28253 amplitude=4.5821247 duration=939.16724
Composer: Produced note #47 having frequency=117.955925 amplitude=10.678089 duration=610.817
Composer: Produced note #48 having frequency=861.7317 amplitude=41.49949 duration=876.11206
Composer: Produced note #49 having frequency=832.9908 amplitude=1.2113631 duration=55.149555
Composer: Produced note #50 having frequency=17.716228 amplitude=20.559479 duration=914.0913
NoiseFilter: Accepted Note: frequency=32.63432 amplitude=37.474403 duration=517.95953
NoiseFilter: Too quiet or too loud: volume=5.8644085
NoiseFilter: Too quiet or too loud: volume=15.141705
NoiseFilter: Too quiet or too loud: volume=24.064396
NoiseFilter: Too quiet or too loud: volume=25.309376
DurationFilter: Accepted Note: frequency=32.63432 amplitude=37.474403 duration=517.95953
Composer: Produced note #51 having frequency=135.01399 amplitude=29.588116 duration=806.37286
NoiseFilter: Too quiet or too loud: volume=23.650368
Amplification: Amplified from old amplitude=4.5821247 to new amplitude=-5.4178753
NoiseFilter: Too quiet or too loud: volume=23.093323
Composer: Produced note #52 having frequency=694.4408 amplitude=93.43983 duration=381.09302
NoiseFilter: Too quiet or too loud: volume=-3.9993439
Amplification: Amplified from old amplitude=10.678089 to new amplitude=0.67808914
Amplification: Amplified from old amplitude=41.49949 to new amplitude=31.499489
NoiseFilter: Accepted Note: frequency=674.6071 amplitude=80.57586 duration=427.0139
Composer: Produced note #53 having frequency=182.04076 amplitude=36.060314 duration=67.220924
Composer: Produced note #54 having frequency=564.589 amplitude=90.04347 duration=470.52722
DurationFilter: Too short of a duration: duration=427.0139
Composer: Produced note #55 having frequency=382.18033 amplitude=29.488218 duration=961.5636
Amplification: Amplified from old amplitude=1.2113631 to new amplitude=-8.788637
Composer: Produced note #56 having frequency=510.96414 amplitude=24.799341 duration=566.52527
NoiseFilter: Accepted Note: frequency=978.7508 amplitude=59.655052 duration=184.87334
Composer: Produced note #57 having frequency=524.44165 amplitude=83.41902 duration=474.5574
DurationFilter: Too short of a duration: duration=184.87334
Composer: Produced note #58 having frequency=862.7976 amplitude=14.458531 duration=295.58862
NoiseFilter: Too quiet or too loud: volume=14.495167
Composer: Produced note #59 having frequency=352.49292 amplitude=88.42255 duration=350.34866
Amplification: Amplified from old amplitude=20.559479 to new amplitude=10.559479
Composer: Produced note #60 having frequency=416.80872 amplitude=31.110304 duration=608.2004
NoiseFilter: Too quiet or too loud: volume=1.8457203
Composer: Produced note #61 having frequency=917.18866 amplitude=82.24203 duration=428.1286
Amplification: Amplified from old amplitude=29.588116 to new amplitude=19.588116
Composer: Produced note #62 having frequency=144.86372 amplitude=97.415016 duration=733.7719
NoiseFilter: Too quiet or too loud: volume=4.1913357
Composer: Produced note #63 having frequency=149.19572 amplitude=5.336249 duration=811.9452
Amplification: Amplified from old amplitude=93.43983 to new amplitude=83.43983
Composer: Produced note #64 having frequency=145.37967 amplitude=80.49048 duration=123.27302
NoiseFilter: Too quiet or too loud: volume=-0.5595064
Composer: Produced note #65 having frequency=249.05336 amplitude=65.39488 duration=311.79727
Composer: Produced note #66 having frequency=385.14398 amplitude=58.529083 duration=600.53235
Composer: Produced note #67 having frequency=488.91873 amplitude=7.1538267 duration=25.504648
Amplification: Amplified from old amplitude=36.060314 to new amplitude=26.060314
Composer: Produced note #68 having frequency=374.53842 amplitude=43.353237 duration=978.645
Amplification: Amplified from old amplitude=90.04347 to new amplitude=80.04347
Composer: Produced note #69 having frequency=111.94623 amplitude=42.212086 duration=109.00092
NoiseFilter: Accepted Note: frequency=579.95166 amplitude=74.68433 duration=218.02205
Composer: Produced note #70 having frequency=757.3892 amplitude=39.615654 duration=465.30695
DurationFilter: Too short of a duration: duration=218.02205
Composer: Produced note #71 having frequency=146.33244 amplitude=13.417679 duration=786.0307
Amplification: Amplified from old amplitude=29.488218 to new amplitude=19.488218
Composer: Produced note #72 having frequency=681.25256 amplitude=38.53867 duration=181.58221
NoiseFilter: Too quiet or too loud: volume=8.907791
Composer: Produced note #73 having frequency=736.1111 amplitude=90.15044 duration=740.99506
Amplification: Amplified from old amplitude=24.799341 to new amplitude=14.799341
Composer: Produced note #74 having frequency=553.3618 amplitude=74.33552 duration=998.6464
Amplification: Amplified from old amplitude=83.41902 to new amplitude=73.41902
Amplification: Amplified from old amplitude=14.458531 to new amplitude=4.4585314
NoiseFilter: Too quiet or too loud: volume=-5.1826334
Amplification: Amplified from old amplitude=88.42255 to new amplitude=78.42255
Composer: Produced note #75 having frequency=126.5778 amplitude=1.9572735 duration=314.97217
Amplification: Amplified from old amplitude=31.110304 to new amplitude=21.110304
Composer: Produced note #76 having frequency=294.49838 amplitude=87.14086 duration=999.3114
NoiseFilter: Too quiet or too loud: volume=16.092327
Composer: Produced note #77 having frequency=940.2697 amplitude=23.820406 duration=47.28955
Composer: Produced note #78 having frequency=833.6363 amplitude=22.884106 duration=111.187454
Composer: Produced note #79 having frequency=137.11977 amplitude=78.820786 duration=217.76099
Amplification: Amplified from old amplitude=82.24203 to new amplitude=72.24203
Composer: Produced note #80 having frequency=879.0093 amplitude=73.4422 duration=515.4621
Amplification: Amplified from old amplitude=97.415016 to new amplitude=87.415016
Composer: Produced note #81 having frequency=907.1581 amplitude=75.934555 duration=997.3344
NoiseFilter: Accepted Note: frequency=945.6244 amplitude=67.68248 duration=314.40765
Composer: Produced note #82 having frequency=8.077204 amplitude=60.293465 duration=963.18835
DurationFilter: Too short of a duration: duration=314.40765
Composer: Produced note #83 having frequency=139.91153 amplitude=88.35932 duration=769.46246
Amplification: Amplified from old amplitude=5.336249 to new amplitude=-4.663751
Composer: Produced note #84 having frequency=396.37137 amplitude=10.89167 duration=572.87836
NoiseFilter: Too quiet or too loud: volume=-4.758171
Composer: Produced note #85 having frequency=75.84208 amplitude=77.09598 duration=921.1777
Amplification: Amplified from old amplitude=80.49048 to new amplitude=70.49048
Composer: Produced note #86 having frequency=750.1031 amplitude=72.4182 duration=123.91752
Amplification: Amplified from old amplitude=65.39488 to new amplitude=55.394882
Composer: Produced note #87 having frequency=511.31076 amplitude=34.139668 duration=290.81732
NoiseFilter: Too quiet or too loud: volume=0.15350819
Composer: Produced note #88 having frequency=329.0962 amplitude=45.938564 duration=834.5763
Amplification: Amplified from old amplitude=58.529083 to new amplitude=48.529083
Composer: Produced note #89 having frequency=799.05164 amplitude=93.39574 duration=345.7766
Amplification: Amplified from old amplitude=7.1538267 to new amplitude=-2.8461733
Composer: Produced note #90 having frequency=57.22016 amplitude=35.39582 duration=935.8622
Amplification: Amplified from old amplitude=43.353237 to new amplitude=33.353237
NoiseFilter: Too quiet or too loud: volume=1.2131233
NoiseFilter: Too quiet or too loud: volume=5.0056477
Amplification: Amplified from old amplitude=42.212086 to new amplitude=32.212086
Amplification: Amplified from old amplitude=39.615654 to new amplitude=29.615654
Amplification: Amplified from old amplitude=13.417679 to new amplitude=3.4176788
NoiseFilter: Accepted Note: frequency=638.0857 amplitude=80.18123 duration=487.23166
Amplification: Amplified from old amplitude=38.53867 to new amplitude=28.53867
DurationFilter: Too short of a duration: duration=487.23166
Amplification: Amplified from old amplitude=90.15044 to new amplitude=80.15044
Amplification: Amplified from old amplitude=74.33552 to new amplitude=64.33552
Amplification: Amplified from old amplitude=1.9572735 to new amplitude=-8.0427265
NoiseFilter: Accepted Note: frequency=416.32056 amplitude=65.39333 duration=850.9158
Amplification: Amplified from old amplitude=87.14086 to new amplitude=77.14086
Amplification: Amplified from old amplitude=23.820406 to new amplitude=13.820406
Amplification: Amplified from old amplitude=22.884106 to new amplitude=12.884106
Amplification: Amplified from old amplitude=78.820786 to new amplitude=68.820786
Amplification: Amplified from old amplitude=73.4422 to new amplitude=63.4422
Amplification: Amplified from old amplitude=75.934555 to new amplitude=65.934555
DurationFilter: Accepted Note: frequency=416.32056 amplitude=65.39333 duration=850.9158
Composer: Produced note #91 having frequency=470.93875 amplitude=85.25648 duration=670.1153
Amplification: Amplified from old amplitude=60.293465 to new amplitude=50.293465
NoiseFilter: Accepted Note: frequency=38.836777 amplitude=83.72508 duration=725.43646
Composer: Produced note #92 having frequency=238.4767 amplitude=90.080246 duration=152.17972
NoiseFilter: Accepted Note: frequency=365.55237 amplitude=42.107655 duration=497.75327
NoiseFilter: Too quiet or too loud: volume=-0.95396996
NoiseFilter: Too quiet or too loud: volume=19.247368
DurationFilter: Accepted Note: frequency=38.836777 amplitude=83.72508 duration=725.43646
NoiseFilter: Too quiet or too loud: volume=20.568987
DurationFilter: Too short of a duration: duration=497.75327
NoiseFilter: Accepted Note: frequency=656.6849 amplitude=37.770084 duration=2.1500587
NoiseFilter: Too quiet or too loud: volume=27.815636
DurationFilter: Too short of a duration: duration=2.1500587
NoiseFilter: Accepted Note: frequency=400.2049 amplitude=53.307346 duration=408.40912
DurationFilter: Too short of a duration: duration=408.40912
NoiseFilter: Accepted Note: frequency=801.8046 amplitude=41.516884 duration=836.0898
NoiseFilter: Accepted Note: frequency=776.3235 amplitude=57.57012 duration=681.48035
DurationFilter: Accepted Note: frequency=801.8046 amplitude=41.516884 duration=836.0898
NoiseFilter: Accepted Note: frequency=622.6718 amplitude=72.813354 duration=140.88803
DurationFilter: Accepted Note: frequency=776.3235 amplitude=57.57012 duration=681.48035
DurationFilter: Too short of a duration: duration=140.88803
NoiseFilter: Accepted Note: frequency=663.53094 amplitude=84.65215 duration=559.6151
NoiseFilter: Too quiet or too loud: volume=12.495985
Amplification: Amplified from old amplitude=88.35932 to new amplitude=78.35932
NoiseFilter: Accepted Note: frequency=707.3706 amplitude=86.40983 duration=753.9628
DurationFilter: Accepted Note: frequency=663.53094 amplitude=84.65215 duration=559.6151
NoiseFilter: Accepted Note: frequency=961.61957 amplitude=72.48189 duration=873.3244
DurationFilter: Accepted Note: frequency=707.3706 amplitude=86.40983 duration=753.9628
NoiseFilter: Accepted Note: frequency=857.7434 amplitude=64.34756 duration=640.03455
NoiseFilter: Frequency is inaudible: frequency=15.772999
DurationFilter: Accepted Note: frequency=961.61957 amplitude=72.48189 duration=873.3244
NoiseFilter: Accepted Note: frequency=357.31317 amplitude=35.14454 duration=775.8356
NoiseFilter: Too quiet or too loud: volume=-5.4178753
DurationFilter: Accepted Note: frequency=857.7434 amplitude=64.34756 duration=640.03455
Amplification: Amplified from old amplitude=10.89167 to new amplitude=0.8916702
Amplification: Amplified from old amplitude=77.09598 to new amplitude=67.09598
DurationFilter: Accepted Note: frequency=357.31317 amplitude=35.14454 duration=775.8356
Amplification: Amplified from old amplitude=72.4182 to new amplitude=62.418198
NoiseFilter: Too quiet or too loud: volume=0.67808914
Amplification: Amplified from old amplitude=34.139668 to new amplitude=24.139668
Amplification: Amplified from old amplitude=45.938564 to new amplitude=35.938564
Amplification: Amplified from old amplitude=93.39574 to new amplitude=83.39574
NoiseFilter: Accepted Note: frequency=861.7317 amplitude=31.499489 duration=876.11206
Amplification: Amplified from old amplitude=35.39582 to new amplitude=25.39582
NoiseFilter: Too quiet or too loud: volume=-8.788637
Amplification: Amplified from old amplitude=85.25648 to new amplitude=75.25648
Amplification: Amplified from old amplitude=90.080246 to new amplitude=80.080246
NoiseFilter: Frequency is inaudible: frequency=17.716228
NoiseFilter: Too quiet or too loud: volume=19.588116
Composer: Produced note #93 having frequency=441.32065 amplitude=37.26582 duration=241.26619
NoiseFilter: The note is suspicious: amplitude=83.43983 predecessor's amplitude=31.499489
Composer: Produced note #94 having frequency=803.27924 amplitude=14.473433 duration=622.59424
DurationFilter: Accepted Note: frequency=861.7317 amplitude=31.499489 duration=876.11206
Composer: Produced note #95 having frequency=813.0137 amplitude=90.17327 duration=384.9646
Amplification: Amplified from old amplitude=37.26582 to new amplitude=27.26582
NoiseFilter: Too quiet or too loud: volume=26.060314
Amplification: Amplified from old amplitude=14.473433 to new amplitude=4.4734325
Amplification: Amplified from old amplitude=90.17327 to new amplitude=80.17327
Composer: Produced note #96 having frequency=722.4014 amplitude=50.931213 duration=461.68292
NoiseFilter: Accepted Note: frequency=564.589 amplitude=80.04347 duration=470.52722
NoiseFilter: Too quiet or too loud: volume=19.488218
DurationFilter: Too short of a duration: duration=470.52722
Composer: Produced note #97 having frequency=228.26273 amplitude=10.183323 duration=287.37735
NoiseFilter: Too quiet or too loud: volume=14.799341
Composer: Produced note #98 having frequency=200.76924 amplitude=90.44582 duration=274.9101
Composer: Produced note #99 having frequency=971.39685 amplitude=58.184845 duration=926.06366
Shutting down Producer: DigitalComposer
NoiseFilter: Accepted Note: frequency=524.44165 amplitude=73.41902 duration=474.5574
NoiseFilter: Too quiet or too loud: volume=4.4585314
Amplification: Amplified from old amplitude=50.931213 to new amplitude=40.931213
Amplification: Amplified from old amplitude=10.183323 to new amplitude=0.1833229
Amplification: Amplified from old amplitude=90.44582 to new amplitude=80.44582
DurationFilter: Too short of a duration: duration=474.5574
Amplification: Amplified from old amplitude=58.184845 to new amplitude=48.184845
Shutting down Transformer: Amplifier
NoiseFilter: Accepted Note: frequency=352.49292 amplitude=78.42255 duration=350.34866
NoiseFilter: Too quiet or too loud: volume=21.110304
NoiseFilter: Accepted Note: frequency=917.18866 amplitude=72.24203 duration=428.1286
NoiseFilter: Accepted Note: frequency=144.86372 amplitude=87.415016 duration=733.7719
NoiseFilter: Too quiet or too loud: volume=-4.663751
NoiseFilter: Accepted Note: frequency=145.37967 amplitude=70.49048 duration=123.27302
NoiseFilter: Accepted Note: frequency=249.05336 amplitude=55.394882 duration=311.79727
DurationFilter: Too short of a duration: duration=350.34866
NoiseFilter: Accepted Note: frequency=385.14398 amplitude=48.529083 duration=600.53235
DurationFilter: Too short of a duration: duration=428.1286
NoiseFilter: Too quiet or too loud: volume=-2.8461733
DurationFilter: Accepted Note: frequency=144.86372 amplitude=87.415016 duration=733.7719
DurationFilter: Too short of a duration: duration=123.27302
NoiseFilter: Accepted Note: frequency=374.53842 amplitude=33.353237 duration=978.645
NoiseFilter: Accepted Note: frequency=111.94623 amplitude=32.212086 duration=109.00092
NoiseFilter: Too quiet or too loud: volume=29.615654
NoiseFilter: Too quiet or too loud: volume=3.4176788
NoiseFilter: Too quiet or too loud: volume=28.53867
NoiseFilter: Accepted Note: frequency=736.1111 amplitude=80.15044 duration=740.99506
NoiseFilter: Accepted Note: frequency=553.3618 amplitude=64.33552 duration=998.6464
NoiseFilter: Too quiet or too loud: volume=-8.0427265
NoiseFilter: Accepted Note: frequency=294.49838 amplitude=77.14086 duration=999.3114
NoiseFilter: Too quiet or too loud: volume=13.820406
NoiseFilter: Too quiet or too loud: volume=12.884106
NoiseFilter: Accepted Note: frequency=137.11977 amplitude=68.820786 duration=217.76099
DurationFilter: Too short of a duration: duration=311.79727
DurationFilter: Accepted Note: frequency=385.14398 amplitude=48.529083 duration=600.53235
DurationFilter: Accepted Note: frequency=374.53842 amplitude=33.353237 duration=978.645
DurationFilter: Too short of a duration: duration=109.00092
DurationFilter: Accepted Note: frequency=736.1111 amplitude=80.15044 duration=740.99506
DurationFilter: Accepted Note: frequency=553.3618 amplitude=64.33552 duration=998.6464
DurationFilter: Accepted Note: frequency=294.49838 amplitude=77.14086 duration=999.3114
DurationFilter: Too short of a duration: duration=217.76099
NoiseFilter: Accepted Note: frequency=879.0093 amplitude=63.4422 duration=515.4621
NoiseFilter: Accepted Note: frequency=907.1581 amplitude=65.934555 duration=997.3344
NoiseFilter: Frequency is inaudible: frequency=8.077204
DurationFilter: Accepted Note: frequency=879.0093 amplitude=63.4422 duration=515.4621
NoiseFilter: Accepted Note: frequency=139.91153 amplitude=78.35932 duration=769.46246
NoiseFilter: Too quiet or too loud: volume=0.8916702
DurationFilter: Accepted Note: frequency=907.1581 amplitude=65.934555 duration=997.3344
NoiseFilter: Accepted Note: frequency=75.84208 amplitude=67.09598 duration=921.1777
DurationFilter: Accepted Note: frequency=139.91153 amplitude=78.35932 duration=769.46246
NoiseFilter: Accepted Note: frequency=750.1031 amplitude=62.418198 duration=123.91752
NoiseFilter: Too quiet or too loud: volume=24.139668
DurationFilter: Accepted Note: frequency=75.84208 amplitude=67.09598 duration=921.1777
DurationFilter: Too short of a duration: duration=123.91752
NoiseFilter: Accepted Note: frequency=329.0962 amplitude=35.938564 duration=834.5763
NoiseFilter: Accepted Note: frequency=799.05164 amplitude=83.39574 duration=345.7766
DurationFilter: Accepted Note: frequency=329.0962 amplitude=35.938564 duration=834.5763
DurationFilter: Too short of a duration: duration=345.7766
NoiseFilter: Too quiet or too loud: volume=25.39582
NoiseFilter: Accepted Note: frequency=470.93875 amplitude=75.25648 duration=670.1153
NoiseFilter: Accepted Note: frequency=238.4767 amplitude=80.080246 duration=152.17972
DurationFilter: Accepted Note: frequency=470.93875 amplitude=75.25648 duration=670.1153
DurationFilter: Too short of a duration: duration=152.17972
NoiseFilter: Too quiet or too loud: volume=27.26582
NoiseFilter: Too quiet or too loud: volume=4.4734325
NoiseFilter: Accepted Note: frequency=813.0137 amplitude=80.17327 duration=384.9646
DurationFilter: Too short of a duration: duration=384.9646
NoiseFilter: Accepted Note: frequency=722.4014 amplitude=40.931213 duration=461.68292
NoiseFilter: Too quiet or too loud: volume=0.1833229
DurationFilter: Too short of a duration: duration=461.68292
NoiseFilter: Accepted Note: frequency=200.76924 amplitude=80.44582 duration=274.9101
Shutting down Tester: NoiseFilter
DurationFilter: Too short of a duration: duration=274.9101
Shutting down Tester: DurationFilter
Playing: frequency=32.63432 amplitude=37.474403 duration=517.95953
Playing: frequency=416.32056 amplitude=65.39333 duration=850.9158
Playing: frequency=38.836777 amplitude=83.72508 duration=725.43646
Playing: frequency=801.8046 amplitude=41.516884 duration=836.0898
Playing: frequency=776.3235 amplitude=57.57012 duration=681.48035
Playing: frequency=663.53094 amplitude=84.65215 duration=559.6151
Playing: frequency=707.3706 amplitude=86.40983 duration=753.9628
Playing: frequency=961.61957 amplitude=72.48189 duration=873.3244
Playing: frequency=857.7434 amplitude=64.34756 duration=640.03455
Playing: frequency=357.31317 amplitude=35.14454 duration=775.8356
Playing: frequency=861.7317 amplitude=31.499489 duration=876.11206
Playing: frequency=144.86372 amplitude=87.415016 duration=733.7719
Playing: frequency=385.14398 amplitude=48.529083 duration=600.53235
Playing: frequency=374.53842 amplitude=33.353237 duration=978.645
Playing: frequency=736.1111 amplitude=80.15044 duration=740.99506
Playing: frequency=553.3618 amplitude=64.33552 duration=998.6464
Playing: frequency=294.49838 amplitude=77.14086 duration=999.3114
Playing: frequency=879.0093 amplitude=63.4422 duration=515.4621
Playing: frequency=907.1581 amplitude=65.934555 duration=997.3344
Playing: frequency=139.91153 amplitude=78.35932 duration=769.46246
Playing: frequency=75.84208 amplitude=67.09598 duration=921.1777
Playing: frequency=329.0962 amplitude=35.938564 duration=834.5763
Playing: frequency=470.93875 amplitude=75.25648 duration=670.1153
Playing: frequency=971.39685 amplitude=48.184845 duration=926.06366
Shutting down Consumer: Player
Stop dancing!! Music is over~~~
```


* [Airline Performance Analyzer](http://www.cs.sjsu.edu/faculty/pearce/modules/projects/streams/index.htm)
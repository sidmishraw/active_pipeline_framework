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
* GarBage Band: GarBage Band is a digital music studio that processes streams of musical notes. Each note has frequency, amplitude, and duration. (These can be floating point numbers.) GB provides the following configurable filters:

  * Amplifiers: Increases or decreases the volume of each note
  * Players: Play the notes through the computer's sound system.
  * Noise Filters: Remove noise notes from the stream. A noise note can be a note that doesn't make sense: too loud, too quiet, too short, inaudible frequency, or a note that's suspiciously different from its predecessor (this might be static or a pop).
  * Digital Composer: Generates a random sequence of <= 100 notes.


Output for GarBage Band when run for producing Notes on a 'Piano':
```
Starting the GarBage Band application... start dancing.. PepePls!
Started Producer
Started Transformer
Started Tester
Started Consumer
Composer: Produced note# 0having frequency=666.2013 amplitude=20.21026 duration=472.5647
Composer: Produced note# 1having frequency=517.35126 amplitude=52.87549 duration=910.1466
Composer: Produced note# 2having frequency=746.4379 amplitude=28.557037 duration=110.38458
Amplification: Amplified from old amplitude=20.21026 to new amplitude=10.21026
Composer: Produced note# 3having frequency=280.36667 amplitude=38.065254 duration=542.3858
Amplification: Amplified from old amplitude=52.87549 to new amplitude=42.87549
Filter: Too quiet or too loud: volume=10.21026
Amplification: Amplified from old amplitude=28.557037 to new amplitude=18.557037
Composer: Produced note# 4having frequency=58.855713 amplitude=55.40378 duration=505.97687
Amplification: Amplified from old amplitude=38.065254 to new amplitude=28.065254
Composer: Produced note# 5having frequency=434.61902 amplitude=7.324725 duration=718.5907
Amplification: Amplified from old amplitude=55.40378 to new amplitude=45.40378
Composer: Produced note# 6having frequency=987.0742 amplitude=56.52883 duration=922.0839
Amplification: Amplified from old amplitude=7.324725 to new amplitude=-2.6752748
Composer: Produced note# 7having frequency=255.86963 amplitude=98.53513 duration=565.3445
Amplification: Amplified from old amplitude=56.52883 to new amplitude=46.52883
Composer: Produced note# 8having frequency=631.3636 amplitude=50.749893 duration=613.93634
Amplification: Amplified from old amplitude=98.53513 to new amplitude=88.53513
Composer: Produced note# 9having frequency=261.63293 amplitude=71.503265 duration=827.18097
Amplification: Amplified from old amplitude=50.749893 to new amplitude=40.749893
Composer: Produced note# 10having frequency=480.56073 amplitude=22.208225 duration=264.3794
Amplification: Amplified from old amplitude=71.503265 to new amplitude=61.503265
Composer: Produced note# 11having frequency=814.16455 amplitude=54.042625 duration=684.5597
Amplification: Amplified from old amplitude=22.208225 to new amplitude=12.208225
Composer: Produced note# 12having frequency=142.19206 amplitude=38.37043 duration=526.0545
Amplification: Amplified from old amplitude=54.042625 to new amplitude=44.042625
Composer: Produced note# 13having frequency=422.44815 amplitude=44.079243 duration=618.44415
Amplification: Amplified from old amplitude=38.37043 to new amplitude=28.37043
Composer: Produced note# 14having frequency=899.5592 amplitude=68.05462 duration=975.10565
Amplification: Amplified from old amplitude=44.079243 to new amplitude=34.079243
Composer: Produced note# 15having frequency=271.6995 amplitude=10.400266 duration=184.79932
Amplification: Amplified from old amplitude=68.05462 to new amplitude=58.05462
Composer: Produced note# 16having frequency=50.792038 amplitude=35.00771 duration=993.31287
Amplification: Amplified from old amplitude=10.400266 to new amplitude=0.4002657
Composer: Produced note# 17having frequency=653.92566 amplitude=45.966457 duration=607.94293
Amplification: Amplified from old amplitude=35.00771 to new amplitude=25.00771
Composer: Produced note# 18having frequency=929.5224 amplitude=35.879826 duration=165.32904
Amplification: Amplified from old amplitude=45.966457 to new amplitude=35.966457
Composer: Produced note# 19having frequency=826.1346 amplitude=11.934823 duration=944.0334
Amplification: Amplified from old amplitude=35.879826 to new amplitude=25.879826
Composer: Produced note# 20having frequency=937.1832 amplitude=13.122535 duration=927.2219
Amplification: Amplified from old amplitude=11.934823 to new amplitude=1.934823
Composer: Produced note# 21having frequency=604.1622 amplitude=85.4483 duration=72.67159
Amplification: Amplified from old amplitude=13.122535 to new amplitude=3.1225348
Composer: Produced note# 22having frequency=177.36703 amplitude=26.984066 duration=359.37695
Amplification: Amplified from old amplitude=85.4483 to new amplitude=75.4483
Composer: Produced note# 23having frequency=614.77435 amplitude=62.244312 duration=312.80273
Amplification: Amplified from old amplitude=26.984066 to new amplitude=16.984066
Composer: Produced note# 24having frequency=204.29617 amplitude=9.309256 duration=820.9541
Amplification: Amplified from old amplitude=62.244312 to new amplitude=52.244312
Composer: Produced note# 25having frequency=327.57144 amplitude=71.858604 duration=646.1734
Amplification: Amplified from old amplitude=9.309256 to new amplitude=-0.6907444
Composer: Produced note# 26having frequency=651.43414 amplitude=34.95506 duration=137.0179
Amplification: Amplified from old amplitude=71.858604 to new amplitude=61.858604
Composer: Produced note# 27having frequency=153.32973 amplitude=93.41374 duration=418.0556
Amplification: Amplified from old amplitude=34.95506 to new amplitude=24.95506
Composer: Produced note# 28having frequency=414.6791 amplitude=52.442963 duration=158.43904
Amplification: Amplified from old amplitude=93.41374 to new amplitude=83.41374
Composer: Produced note# 29having frequency=23.392141 amplitude=91.97237 duration=851.8904
Amplification: Amplified from old amplitude=52.442963 to new amplitude=42.442963
Composer: Produced note# 30having frequency=548.9251 amplitude=39.164627 duration=543.0155
Amplification: Amplified from old amplitude=91.97237 to new amplitude=81.97237
Composer: Produced note# 31having frequency=882.9527 amplitude=47.59226 duration=172.02968
Amplification: Amplified from old amplitude=39.164627 to new amplitude=29.164627
Composer: Produced note# 32having frequency=164.80565 amplitude=46.339478 duration=966.7903
Amplification: Amplified from old amplitude=47.59226 to new amplitude=37.59226
Composer: Produced note# 33having frequency=435.46384 amplitude=69.92306 duration=693.8895
Amplification: Amplified from old amplitude=46.339478 to new amplitude=36.339478
Composer: Produced note# 34having frequency=274.73355 amplitude=52.957386 duration=825.00323
Amplification: Amplified from old amplitude=69.92306 to new amplitude=59.923058
Composer: Produced note# 35having frequency=360.32672 amplitude=2.8280973 duration=455.24185
Amplification: Amplified from old amplitude=52.957386 to new amplitude=42.957386
Composer: Produced note# 36having frequency=489.34912 amplitude=14.750117 duration=699.22546
Amplification: Amplified from old amplitude=2.8280973 to new amplitude=-7.1719027
Composer: Produced note# 37having frequency=757.4412 amplitude=26.632011 duration=624.20087
Amplification: Amplified from old amplitude=14.750117 to new amplitude=4.7501173
Composer: Produced note# 38having frequency=363.96866 amplitude=84.586105 duration=134.33362
Amplification: Amplified from old amplitude=26.632011 to new amplitude=16.632011
Composer: Produced note# 39having frequency=121.6976 amplitude=94.26107 duration=302.83417
Composer: Produced note# 40having frequency=298.66855 amplitude=69.721634 duration=908.7754
Composer: Produced note# 41having frequency=156.67838 amplitude=9.76981 duration=521.61584
Composer: Produced note# 42having frequency=279.38974 amplitude=30.799187 duration=889.70374
Composer: Produced note# 43having frequency=438.94666 amplitude=75.57257 duration=790.57806
Composer: Produced note# 44having frequency=442.9871 amplitude=68.31447 duration=134.18555
Composer: Produced note# 45having frequency=937.9618 amplitude=48.088284 duration=961.64465
Composer: Produced note# 46having frequency=11.113346 amplitude=37.720905 duration=383.51096
Composer: Produced note# 47having frequency=695.26636 amplitude=78.15913 duration=615.583
Composer: Produced note# 48having frequency=275.88336 amplitude=53.80938 duration=689.5791
Composer: Produced note# 49having frequency=595.88074 amplitude=52.041607 duration=140.09744
Composer: Produced note# 50having frequency=352.06915 amplitude=1.9580543 duration=534.02356
Composer: Produced note# 51having frequency=659.19214 amplitude=34.418194 duration=246.38945
Composer: Produced note# 52having frequency=852.48645 amplitude=97.165955 duration=744.62427
Composer: Produced note# 53having frequency=208.18376 amplitude=12.750875 duration=359.94183
Composer: Produced note# 54having frequency=24.648964 amplitude=29.869534 duration=544.828
Composer: Produced note# 55having frequency=340.6369 amplitude=60.89042 duration=708.82385
̄Filter: Accepted Note: frequency=517.35126 amplitude=42.87549 duration=910.1466
Composer: Produced note# 56having frequency=480.34613 amplitude=82.934944 duration=732.01886
Amplification: Amplified from old amplitude=84.586105 to new amplitude=74.586105
Composer: Produced note# 57having frequency=654.46106 amplitude=54.55089 duration=954.63007
Composer: Produced note# 58having frequency=949.218 amplitude=7.379186 duration=864.6279
Composer: Produced note# 59having frequency=269.77063 amplitude=36.80558 duration=720.3827
Filter: Too quiet or too loud: volume=18.557037
Composer: Produced note# 60having frequency=543.04834 amplitude=3.3479095 duration=333.81677
Composer: Produced note# 61having frequency=857.4629 amplitude=55.77758 duration=395.57123
Playing: frequency=517.35126 amplitude=42.87549 duration=910.1466
Amplification: Amplified from old amplitude=94.26107 to new amplitude=84.26107
Composer: Produced note# 62having frequency=703.00543 amplitude=86.29117 duration=333.3736
Filter: Too quiet or too loud: volume=28.065254
Composer: Produced note# 63having frequency=494.3766 amplitude=30.659496 duration=158.23615
Composer: Produced note# 64having frequency=569.05884 amplitude=58.02397 duration=238.51067
Composer: Produced note# 65having frequency=420.6333 amplitude=15.107369 duration=967.82745
Composer: Produced note# 66having frequency=411.30316 amplitude=94.18152 duration=326.66223
̄Filter: Accepted Note: frequency=58.855713 amplitude=45.40378 duration=505.97687
Amplification: Amplified from old amplitude=69.721634 to new amplitude=59.721634
Amplification: Amplified from old amplitude=9.76981 to new amplitude=-0.23019028
Amplification: Amplified from old amplitude=30.799187 to new amplitude=20.799187
Amplification: Amplified from old amplitude=75.57257 to new amplitude=65.57257
Amplification: Amplified from old amplitude=68.31447 to new amplitude=58.31447
Amplification: Amplified from old amplitude=48.088284 to new amplitude=38.088284
Amplification: Amplified from old amplitude=37.720905 to new amplitude=27.720905
Amplification: Amplified from old amplitude=78.15913 to new amplitude=68.15913
Amplification: Amplified from old amplitude=53.80938 to new amplitude=43.80938
Amplification: Amplified from old amplitude=52.041607 to new amplitude=42.041607
Amplification: Amplified from old amplitude=1.9580543 to new amplitude=-8.041945
Amplification: Amplified from old amplitude=34.418194 to new amplitude=24.418194
Amplification: Amplified from old amplitude=97.165955 to new amplitude=87.165955
Amplification: Amplified from old amplitude=12.750875 to new amplitude=2.7508755
Amplification: Amplified from old amplitude=29.869534 to new amplitude=19.869534
Amplification: Amplified from old amplitude=60.89042 to new amplitude=50.89042
Amplification: Amplified from old amplitude=82.934944 to new amplitude=72.934944
Amplification: Amplified from old amplitude=54.55089 to new amplitude=44.55089
Amplification: Amplified from old amplitude=7.379186 to new amplitude=-2.6208138
Filter: Too quiet or too loud: volume=-2.6752748
Composer: Produced note# 67having frequency=429.38852 amplitude=31.580532 duration=922.2554
Composer: Produced note# 68having frequency=630.1711 amplitude=34.93701 duration=67.34413
Amplification: Amplified from old amplitude=36.80558 to new amplitude=26.80558
Composer: Produced note# 69having frequency=701.91095 amplitude=67.58724 duration=512.9472
Amplification: Amplified from old amplitude=3.3479095 to new amplitude=-6.6520905
Composer: Produced note# 70having frequency=981.95496 amplitude=2.1046937 duration=612.3482
̄Filter: Accepted Note: frequency=987.0742 amplitude=46.52883 duration=922.0839
Composer: Produced note# 71having frequency=527.26434 amplitude=43.72329 duration=541.5026
Composer: Produced note# 72having frequency=191.45071 amplitude=71.18006 duration=21.718681
Composer: Produced note# 73having frequency=662.07965 amplitude=3.1639814 duration=153.99855
̄Filter: Accepted Note: frequency=255.86963 amplitude=88.53513 duration=565.3445
Composer: Produced note# 74having frequency=537.86176 amplitude=24.446465 duration=201.23082
Composer: Produced note# 75having frequency=130.83684 amplitude=98.614174 duration=929.643
Composer: Produced note# 76having frequency=204.79286 amplitude=58.64599 duration=523.8261
Amplification: Amplified from old amplitude=55.77758 to new amplitude=45.77758
Composer: Produced note# 77having frequency=342.86832 amplitude=29.756344 duration=276.83252
̄Filter: Accepted Note: frequency=631.3636 amplitude=40.749893 duration=613.93634
Amplification: Amplified from old amplitude=86.29117 to new amplitude=76.29117
̄Filter: Accepted Note: frequency=261.63293 amplitude=61.503265 duration=827.18097
Filter: Too quiet or too loud: volume=12.208225
̄Filter: Accepted Note: frequency=814.16455 amplitude=44.042625 duration=684.5597
Filter: Too quiet or too loud: volume=28.37043
̄Filter: Accepted Note: frequency=422.44815 amplitude=34.079243 duration=618.44415
̄Filter: Accepted Note: frequency=899.5592 amplitude=58.05462 duration=975.10565
Filter: Too quiet or too loud: volume=0.4002657
Filter: Too quiet or too loud: volume=25.00771
̄Filter: Accepted Note: frequency=653.92566 amplitude=35.966457 duration=607.94293
Filter: Too quiet or too loud: volume=25.879826
Filter: Too quiet or too loud: volume=1.934823
Filter: Too quiet or too loud: volume=3.1225348
Filter: Too short of a duration: duration=72.67159
Filter: Too quiet or too loud: volume=16.984066
Filter: Too short of a duration: duration=312.80273
Filter: Too quiet or too loud: volume=-0.6907444
̄Filter: Accepted Note: frequency=327.57144 amplitude=61.858604 duration=646.1734
Filter: Too quiet or too loud: volume=24.95506
Filter: Too short of a duration: duration=418.0556
Filter: Too short of a duration: duration=158.43904
̄Filter: Accepted Note: frequency=23.392141 amplitude=81.97237 duration=851.8904
Filter: Too quiet or too loud: volume=29.164627
Filter: Too short of a duration: duration=172.02968
̄Filter: Accepted Note: frequency=164.80565 amplitude=36.339478 duration=966.7903
Composer: Produced note# 78having frequency=856.9109 amplitude=41.6107 duration=473.07986
̄Filter: Accepted Note: frequency=435.46384 amplitude=59.923058 duration=693.8895
Composer: Produced note# 79having frequency=226.68678 amplitude=19.77619 duration=683.8394
Composer: Produced note# 80having frequency=978.27313 amplitude=81.17404 duration=304.05545
Composer: Produced note# 81having frequency=856.86115 amplitude=35.508095 duration=456.07214
̄Filter: Accepted Note: frequency=274.73355 amplitude=42.957386 duration=825.00323
Composer: Produced note# 82having frequency=790.5794 amplitude=38.381584 duration=200.30481
Filter: Too quiet or too loud: volume=-7.1719027
Composer: Produced note# 83having frequency=837.3894 amplitude=7.7598515 duration=198.7564
Filter: Too quiet or too loud: volume=4.7501173
Filter: Too quiet or too loud: volume=16.632011
Filter: Too short of a duration: duration=134.33362
Composer: Produced note# 84having frequency=467.4899 amplitude=96.664314 duration=116.283295
Filter: Too short of a duration: duration=302.83417
Composer: Produced note# 85having frequency=727.7372 amplitude=36.249947 duration=897.0322
Composer: Produced note# 86having frequency=789.8117 amplitude=10.479551 duration=773.44135
Composer: Produced note# 87having frequency=593.93256 amplitude=31.173122 duration=503.5673
Amplification: Amplified from old amplitude=30.659496 to new amplitude=20.659496
Composer: Produced note# 88having frequency=241.96828 amplitude=81.10218 duration=892.5292
̄Filter: Accepted Note: frequency=298.66855 amplitude=59.721634 duration=908.7754
Filter: Too quiet or too loud: volume=-0.23019028
Composer: Produced note# 89having frequency=63.945354 amplitude=47.912933 duration=142.93599
Amplification: Amplified from old amplitude=58.02397 to new amplitude=48.02397
Composer: Produced note# 90having frequency=824.86816 amplitude=58.222122 duration=640.51733
Filter: Too quiet or too loud: volume=20.799187
Composer: Produced note# 91having frequency=511.31744 amplitude=47.20421 duration=419.07822
Composer: Produced note# 92having frequency=633.8017 amplitude=81.656494 duration=100.02166
̄Filter: Accepted Note: frequency=438.94666 amplitude=65.57257 duration=790.57806
Filter: Too short of a duration: duration=134.18555
Amplification: Amplified from old amplitude=15.107369 to new amplitude=5.1073694
Amplification: Amplified from old amplitude=94.18152 to new amplitude=84.18152
Amplification: Amplified from old amplitude=31.580532 to new amplitude=21.580532
Composer: Produced note# 93having frequency=345.38437 amplitude=1.6500592 duration=27.03911
Amplification: Amplified from old amplitude=34.93701 to new amplitude=24.937012
Composer: Produced note# 94having frequency=408.75006 amplitude=85.803345 duration=609.1648
Amplification: Amplified from old amplitude=67.58724 to new amplitude=57.587242
Composer: Produced note# 95having frequency=991.2919 amplitude=44.76502 duration=819.793
̄Filter: Accepted Note: frequency=937.9618 amplitude=38.088284 duration=961.64465
Composer: Produced note# 96having frequency=162.9002 amplitude=16.520988 duration=605.4115
Amplification: Amplified from old amplitude=2.1046937 to new amplitude=-7.8953066
Composer: Produced note# 97having frequency=569.34973 amplitude=1.2085497 duration=881.8511
Filter: Frequency is inaudible: frequency=11.113346
Composer: Produced note# 98having frequency=192.50905 amplitude=59.46504 duration=28.271675
Composer: Produced note# 99having frequency=155.0762 amplitude=15.422857 duration=846.08624
Shutting down Producer
̄Filter: Accepted Note: frequency=695.26636 amplitude=68.15913 duration=615.583
Amplification: Amplified from old amplitude=43.72329 to new amplitude=33.72329
Amplification: Amplified from old amplitude=71.18006 to new amplitude=61.18006
Amplification: Amplified from old amplitude=3.1639814 to new amplitude=-6.8360186
̄Filter: Accepted Note: frequency=275.88336 amplitude=43.80938 duration=689.5791
Filter: Too short of a duration: duration=140.09744
Amplification: Amplified from old amplitude=24.446465 to new amplitude=14.446465
Filter: Too quiet or too loud: volume=-8.041945
Amplification: Amplified from old amplitude=98.614174 to new amplitude=88.614174
Amplification: Amplified from old amplitude=58.64599 to new amplitude=48.64599
Filter: Too quiet or too loud: volume=24.418194
Amplification: Amplified from old amplitude=29.756344 to new amplitude=19.756344
Amplification: Amplified from old amplitude=41.6107 to new amplitude=31.610699
̄Filter: Accepted Note: frequency=852.48645 amplitude=87.165955 duration=744.62427
Amplification: Amplified from old amplitude=19.77619 to new amplitude=9.77619
Filter: Too quiet or too loud: volume=2.7508755
Amplification: Amplified from old amplitude=81.17404 to new amplitude=71.17404
Filter: Too quiet or too loud: volume=19.869534
Amplification: Amplified from old amplitude=35.508095 to new amplitude=25.508095
Amplification: Amplified from old amplitude=38.381584 to new amplitude=28.381584
Amplification: Amplified from old amplitude=7.7598515 to new amplitude=-2.2401485
̄Filter: Accepted Note: frequency=340.6369 amplitude=50.89042 duration=708.82385
Amplification: Amplified from old amplitude=96.664314 to new amplitude=86.664314
Amplification: Amplified from old amplitude=36.249947 to new amplitude=26.249947
Amplification: Amplified from old amplitude=10.479551 to new amplitude=0.47955132
̄Filter: Accepted Note: frequency=480.34613 amplitude=72.934944 duration=732.01886
Amplification: Amplified from old amplitude=31.173122 to new amplitude=21.173122
Amplification: Amplified from old amplitude=81.10218 to new amplitude=71.10218
̄Filter: Accepted Note: frequency=654.46106 amplitude=44.55089 duration=954.63007
Amplification: Amplified from old amplitude=47.912933 to new amplitude=37.912933
Filter: Too quiet or too loud: volume=-2.6208138
Filter: Too quiet or too loud: volume=26.80558
Amplification: Amplified from old amplitude=58.222122 to new amplitude=48.222122
Filter: Too quiet or too loud: volume=-6.6520905
Filter: Too short of a duration: duration=395.57123
Filter: Too short of a duration: duration=333.3736
Filter: Too quiet or too loud: volume=20.659496
Filter: Too short of a duration: duration=238.51067
Filter: Too quiet or too loud: volume=5.1073694
Amplification: Amplified from old amplitude=47.20421 to new amplitude=37.20421
Filter: Too short of a duration: duration=326.66223
Filter: Too quiet or too loud: volume=21.580532
Amplification: Amplified from old amplitude=81.656494 to new amplitude=71.656494
Filter: Too quiet or too loud: volume=24.937012
Amplification: Amplified from old amplitude=1.6500592 to new amplitude=-8.349941
Amplification: Amplified from old amplitude=85.803345 to new amplitude=75.803345
Amplification: Amplified from old amplitude=44.76502 to new amplitude=34.76502
̄Filter: Accepted Note: frequency=701.91095 amplitude=57.587242 duration=512.9472
Amplification: Amplified from old amplitude=16.520988 to new amplitude=6.5209885
Filter: Too quiet or too loud: volume=-7.8953066
Amplification: Amplified from old amplitude=1.2085497 to new amplitude=-8.7914505
Amplification: Amplified from old amplitude=59.46504 to new amplitude=49.46504
Amplification: Amplified from old amplitude=15.422857 to new amplitude=5.4228573
Shutting down Transformer
̄Filter: Accepted Note: frequency=527.26434 amplitude=33.72329 duration=541.5026
Filter: Too short of a duration: duration=21.718681
Filter: Too quiet or too loud: volume=-6.8360186
Filter: Too quiet or too loud: volume=14.446465
Filter: The note is suspicious: amplitude=88.614174 predecessor's amplitude=33.72329
̄Filter: Accepted Note: frequency=204.79286 amplitude=48.64599 duration=523.8261
Filter: Too quiet or too loud: volume=19.756344
Filter: Too short of a duration: duration=473.07986
Filter: Too quiet or too loud: volume=9.77619
Filter: Too short of a duration: duration=304.05545
Filter: Too quiet or too loud: volume=25.508095
Filter: Too quiet or too loud: volume=28.381584
Filter: Too quiet or too loud: volume=-2.2401485
Filter: Too short of a duration: duration=116.283295
Filter: Too quiet or too loud: volume=26.249947
Filter: Too quiet or too loud: volume=0.47955132
Filter: Too quiet or too loud: volume=21.173122
̄Filter: Accepted Note: frequency=241.96828 amplitude=71.10218 duration=892.5292
Filter: Too short of a duration: duration=142.93599
̄Filter: Accepted Note: frequency=824.86816 amplitude=48.222122 duration=640.51733
Filter: Too short of a duration: duration=419.07822
Filter: Too short of a duration: duration=100.02166
Filter: Too quiet or too loud: volume=-8.349941
̄Filter: Accepted Note: frequency=408.75006 amplitude=75.803345 duration=609.1648
̄Filter: Accepted Note: frequency=991.2919 amplitude=34.76502 duration=819.793
Filter: Too quiet or too loud: volume=6.5209885
Filter: Too quiet or too loud: volume=-8.7914505
Filter: Too short of a duration: duration=28.271675
Shutting down Tester
Playing: frequency=58.855713 amplitude=45.40378 duration=505.97687
Playing: frequency=987.0742 amplitude=46.52883 duration=922.0839
Playing: frequency=255.86963 amplitude=88.53513 duration=565.3445
Playing: frequency=631.3636 amplitude=40.749893 duration=613.93634
Playing: frequency=261.63293 amplitude=61.503265 duration=827.18097
Playing: frequency=814.16455 amplitude=44.042625 duration=684.5597
Playing: frequency=422.44815 amplitude=34.079243 duration=618.44415
Playing: frequency=899.5592 amplitude=58.05462 duration=975.10565
Playing: frequency=653.92566 amplitude=35.966457 duration=607.94293
Playing: frequency=327.57144 amplitude=61.858604 duration=646.1734
Playing: frequency=23.392141 amplitude=81.97237 duration=851.8904
Playing: frequency=164.80565 amplitude=36.339478 duration=966.7903
Playing: frequency=435.46384 amplitude=59.923058 duration=693.8895
Playing: frequency=274.73355 amplitude=42.957386 duration=825.00323
Playing: frequency=298.66855 amplitude=59.721634 duration=908.7754
Playing: frequency=438.94666 amplitude=65.57257 duration=790.57806
Playing: frequency=937.9618 amplitude=38.088284 duration=961.64465
Playing: frequency=695.26636 amplitude=68.15913 duration=615.583
Playing: frequency=275.88336 amplitude=43.80938 duration=689.5791
Playing: frequency=852.48645 amplitude=87.165955 duration=744.62427
Playing: frequency=340.6369 amplitude=50.89042 duration=708.82385
Playing: frequency=480.34613 amplitude=72.934944 duration=732.01886
Playing: frequency=654.46106 amplitude=44.55089 duration=954.63007
Playing: frequency=701.91095 amplitude=57.587242 duration=512.9472
Playing: frequency=527.26434 amplitude=33.72329 duration=541.5026
Playing: frequency=204.79286 amplitude=48.64599 duration=523.8261
Playing: frequency=241.96828 amplitude=71.10218 duration=892.5292
Playing: frequency=824.86816 amplitude=48.222122 duration=640.51733
Playing: frequency=408.75006 amplitude=75.803345 duration=609.1648
Playing: frequency=991.2919 amplitude=34.76502 duration=819.793
Playing: frequency=155.0762 amplitude=5.4228573 duration=846.08624
Shutting down Consumer
```


* [Airline Performance Analyzer:](http://www.cs.sjsu.edu/faculty/pearce/modules/projects/streams/index.htm)
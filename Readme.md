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
Started Producer
Started Transformer
Started Tester
Started Tester
Started Consumer
Composer: Produced note# 0having frequency=5.9634447 amplitude=59.2151 duration=55.888535
Composer: Produced note# 1having frequency=554.9244 amplitude=46.560116 duration=958.1524
Amplification: Amplified from old amplitude=59.2151 to new amplitude=49.2151
Amplification: Amplified from old amplitude=46.560116 to new amplitude=36.560116
Composer: Produced note# 2having frequency=158.90163 amplitude=35.554188 duration=159.56331
NoiseFilter: Frequency is inaudible: frequency=5.9634447
Composer: Produced note# 3having frequency=728.8361 amplitude=81.60767 duration=661.3551
Composer: Produced note# 4having frequency=643.0225 amplitude=44.42164 duration=654.08154
Composer: Produced note# 5having frequency=715.21344 amplitude=96.56298 duration=150.99007
Composer: Produced note# 6having frequency=516.01294 amplitude=91.143036 duration=923.6866
Composer: Produced note# 7having frequency=777.227 amplitude=64.893684 duration=303.05536
Composer: Produced note# 8having frequency=419.4101 amplitude=11.873561 duration=63.649475
Composer: Produced note# 9having frequency=835.0233 amplitude=89.67936 duration=3.1362176
Composer: Produced note# 10having frequency=128.15488 amplitude=55.97836 duration=438.7651
Composer: Produced note# 11having frequency=231.19319 amplitude=2.3042083 duration=666.94824
Composer: Produced note# 12having frequency=278.74017 amplitude=82.79412 duration=789.2302
Composer: Produced note# 13having frequency=221.09676 amplitude=47.700344 duration=548.13745
Composer: Produced note# 14having frequency=689.4655 amplitude=13.539373 duration=684.9463
Composer: Produced note# 15having frequency=720.12036 amplitude=71.94771 duration=503.30222
Composer: Produced note# 16having frequency=873.99664 amplitude=34.34937 duration=814.5619
Composer: Produced note# 17having frequency=495.42438 amplitude=16.102589 duration=329.96713
Amplification: Amplified from old amplitude=35.554188 to new amplitude=25.554188
Amplification: Amplified from old amplitude=81.60767 to new amplitude=71.60767
Amplification: Amplified from old amplitude=44.42164 to new amplitude=34.42164
Amplification: Amplified from old amplitude=96.56298 to new amplitude=86.56298
Amplification: Amplified from old amplitude=91.143036 to new amplitude=81.143036
Amplification: Amplified from old amplitude=64.893684 to new amplitude=54.893684
Amplification: Amplified from old amplitude=11.873561 to new amplitude=1.8735609
Amplification: Amplified from old amplitude=89.67936 to new amplitude=79.67936
Amplification: Amplified from old amplitude=55.97836 to new amplitude=45.97836
Amplification: Amplified from old amplitude=2.3042083 to new amplitude=-7.6957917
Amplification: Amplified from old amplitude=82.79412 to new amplitude=72.79412
Amplification: Amplified from old amplitude=47.700344 to new amplitude=37.700344
Amplification: Amplified from old amplitude=13.539373 to new amplitude=3.5393734
Amplification: Amplified from old amplitude=71.94771 to new amplitude=61.94771
Amplification: Amplified from old amplitude=34.34937 to new amplitude=24.34937
Amplification: Amplified from old amplitude=16.102589 to new amplitude=6.1025887
Composer: Produced note# 18having frequency=124.54718 amplitude=39.535927 duration=750.4241
Composer: Produced note# 19having frequency=280.6212 amplitude=68.37787 duration=982.54956
Composer: Produced note# 20having frequency=313.7406 amplitude=4.194909 duration=750.64417
Composer: Produced note# 21having frequency=588.39233 amplitude=69.84558 duration=323.17413
Composer: Produced note# 22having frequency=192.63977 amplitude=49.052036 duration=675.9816
Composer: Produced note# 23having frequency=730.6125 amplitude=55.46587 duration=104.82794
Composer: Produced note# 24having frequency=145.45525 amplitude=26.722204 duration=712.52356
Composer: Produced note# 25having frequency=896.6764 amplitude=21.969885 duration=840.3442
Composer: Produced note# 26having frequency=478.75595 amplitude=11.187649 duration=437.73425
Composer: Produced note# 27having frequency=274.94763 amplitude=50.271816 duration=123.71141
Composer: Produced note# 28having frequency=798.9322 amplitude=91.968956 duration=815.7282
Composer: Produced note# 29having frequency=172.87129 amplitude=76.32643 duration=932.2675
Composer: Produced note# 30having frequency=296.69403 amplitude=0.88478327 duration=672.68066
Composer: Produced note# 31having frequency=307.14374 amplitude=88.77671 duration=73.36348
Composer: Produced note# 32having frequency=960.314 amplitude=6.2237206 duration=126.64587
Composer: Produced note# 33having frequency=58.478355 amplitude=80.0528 duration=593.9475
Amplification: Amplified from old amplitude=39.535927 to new amplitude=29.535927
Composer: Produced note# 34having frequency=164.3725 amplitude=1.4335871 duration=63.743473
Composer: Produced note# 35having frequency=685.0128 amplitude=56.73564 duration=0.21588802
Composer: Produced note# 36having frequency=831.87103 amplitude=66.901436 duration=94.127594
Composer: Produced note# 37having frequency=604.4891 amplitude=67.24332 duration=881.0994
Composer: Produced note# 38having frequency=974.0641 amplitude=24.216621 duration=310.32007
Composer: Produced note# 39having frequency=808.98755 amplitude=69.56861 duration=380.84067
Amplification: Amplified from old amplitude=68.37787 to new amplitude=58.37787
Composer: Produced note# 40having frequency=651.5937 amplitude=66.805855 duration=675.21246
Composer: Produced note# 41having frequency=418.67453 amplitude=30.356657 duration=551.7324
Composer: Produced note# 42having frequency=288.55127 amplitude=81.43971 duration=384.35175
Composer: Produced note# 43having frequency=706.65295 amplitude=20.50237 duration=421.69977
Composer: Produced note# 44having frequency=266.1978 amplitude=5.4778996 duration=425.96866
Amplification: Amplified from old amplitude=4.194909 to new amplitude=-5.805091
Amplification: Amplified from old amplitude=69.84558 to new amplitude=59.84558
Amplification: Amplified from old amplitude=49.052036 to new amplitude=39.052036
Amplification: Amplified from old amplitude=55.46587 to new amplitude=45.46587
Amplification: Amplified from old amplitude=26.722204 to new amplitude=16.722204
Amplification: Amplified from old amplitude=21.969885 to new amplitude=11.969885
Composer: Produced note# 45having frequency=602.2916 amplitude=9.328717 duration=430.44745
Amplification: Amplified from old amplitude=11.187649 to new amplitude=1.1876488
Amplification: Amplified from old amplitude=50.271816 to new amplitude=40.271816
Amplification: Amplified from old amplitude=91.968956 to new amplitude=81.968956
Amplification: Amplified from old amplitude=76.32643 to new amplitude=66.32643
Amplification: Amplified from old amplitude=0.88478327 to new amplitude=-9.115217
Amplification: Amplified from old amplitude=88.77671 to new amplitude=78.77671
NoiseFilter: Accepted Note: frequency=554.9244 amplitude=36.560116 duration=958.1524
NoiseFilter: Too quiet or too loud: volume=25.554188
NoiseFilter: Accepted Note: frequency=728.8361 amplitude=71.60767 duration=661.3551
Amplification: Amplified from old amplitude=6.2237206 to new amplitude=-3.7762794
Amplification: Amplified from old amplitude=80.0528 to new amplitude=70.0528
NoiseFilter: Accepted Note: frequency=643.0225 amplitude=34.42164 duration=654.08154
Amplification: Amplified from old amplitude=1.4335871 to new amplitude=-8.566413
NoiseFilter: The note is suspicious: amplitude=86.56298 predecessor's amplitude=34.42164
Composer: Produced note# 46having frequency=313.19858 amplitude=53.536076 duration=413.47565
Composer: Produced note# 47having frequency=853.5444 amplitude=24.196726 duration=682.4034
NoiseFilter: Accepted Note: frequency=516.01294 amplitude=81.143036 duration=923.6866
Composer: Produced note# 48having frequency=435.5133 amplitude=64.55785 duration=628.08746
Composer: Produced note# 49having frequency=371.16992 amplitude=86.193344 duration=880.9477
NoiseFilter: Accepted Note: frequency=777.227 amplitude=54.893684 duration=303.05536
NoiseFilter: Too quiet or too loud: volume=1.8735609
Amplification: Amplified from old amplitude=56.73564 to new amplitude=46.73564
Amplification: Amplified from old amplitude=66.901436 to new amplitude=56.901436
NoiseFilter: Accepted Note: frequency=835.0233 amplitude=79.67936 duration=3.1362176
DurationFilter: Accepted Note: frequency=554.9244 amplitude=36.560116 duration=958.1524
NoiseFilter: Accepted Note: frequency=128.15488 amplitude=45.97836 duration=438.7651
NoiseFilter: Too quiet or too loud: volume=-7.6957917
Playing: frequency=554.9244 amplitude=36.560116 duration=958.1524
NoiseFilter: Accepted Note: frequency=278.74017 amplitude=72.79412 duration=789.2302
Amplification: Amplified from old amplitude=67.24332 to new amplitude=57.243317
Amplification: Amplified from old amplitude=24.216621 to new amplitude=14.216621
Composer: Produced note# 50having frequency=876.83466 amplitude=41.344822 duration=898.46564
Amplification: Amplified from old amplitude=69.56861 to new amplitude=59.56861
Amplification: Amplified from old amplitude=66.805855 to new amplitude=56.805855
NoiseFilter: Accepted Note: frequency=221.09676 amplitude=37.700344 duration=548.13745
NoiseFilter: Too quiet or too loud: volume=3.5393734
DurationFilter: Accepted Note: frequency=728.8361 amplitude=71.60767 duration=661.3551
NoiseFilter: Accepted Note: frequency=720.12036 amplitude=61.94771 duration=503.30222
NoiseFilter: Too quiet or too loud: volume=24.34937
DurationFilter: Accepted Note: frequency=643.0225 amplitude=34.42164 duration=654.08154
Amplification: Amplified from old amplitude=30.356657 to new amplitude=20.356657
Amplification: Amplified from old amplitude=81.43971 to new amplitude=71.43971
DurationFilter: Accepted Note: frequency=516.01294 amplitude=81.143036 duration=923.6866
Composer: Produced note# 51having frequency=469.01453 amplitude=40.453274 duration=987.1203
DurationFilter: Too short of a duration: duration=303.05536
Amplification: Amplified from old amplitude=20.50237 to new amplitude=10.502371
NoiseFilter: Too quiet or too loud: volume=6.1025887
NoiseFilter: Too quiet or too loud: volume=29.535927
Amplification: Amplified from old amplitude=5.4778996 to new amplitude=-4.5221004
Amplification: Amplified from old amplitude=9.328717 to new amplitude=-0.67128277
NoiseFilter: Accepted Note: frequency=280.6212 amplitude=58.37787 duration=982.54956
NoiseFilter: Too quiet or too loud: volume=-5.805091
DurationFilter: Too short of a duration: duration=3.1362176
DurationFilter: Too short of a duration: duration=438.7651
NoiseFilter: Accepted Note: frequency=588.39233 amplitude=59.84558 duration=323.17413
DurationFilter: Accepted Note: frequency=278.74017 amplitude=72.79412 duration=789.2302
NoiseFilter: Accepted Note: frequency=192.63977 amplitude=39.052036 duration=675.9816
DurationFilter: Accepted Note: frequency=221.09676 amplitude=37.700344 duration=548.13745
NoiseFilter: Accepted Note: frequency=730.6125 amplitude=45.46587 duration=104.82794
NoiseFilter: Too quiet or too loud: volume=16.722204
DurationFilter: Accepted Note: frequency=720.12036 amplitude=61.94771 duration=503.30222
Composer: Produced note# 52having frequency=654.11084 amplitude=66.852844 duration=95.64787
Composer: Produced note# 53having frequency=368.88947 amplitude=83.12224 duration=685.7148
Composer: Produced note# 54having frequency=983.0747 amplitude=58.985485 duration=403.13666
NoiseFilter: Too quiet or too loud: volume=11.969885
Composer: Produced note# 55having frequency=278.03864 amplitude=84.15711 duration=56.507587
Amplification: Amplified from old amplitude=53.536076 to new amplitude=43.536076
Composer: Produced note# 56having frequency=568.0838 amplitude=15.67809 duration=897.2437
Amplification: Amplified from old amplitude=24.196726 to new amplitude=14.196726
Composer: Produced note# 57having frequency=560.125 amplitude=79.987625 duration=253.63766
Composer: Produced note# 58having frequency=293.5035 amplitude=16.26916 duration=865.9465
NoiseFilter: Too quiet or too loud: volume=1.1876488
DurationFilter: Accepted Note: frequency=280.6212 amplitude=58.37787 duration=982.54956
DurationFilter: Too short of a duration: duration=323.17413
NoiseFilter: Accepted Note: frequency=274.94763 amplitude=40.271816 duration=123.71141
DurationFilter: Accepted Note: frequency=192.63977 amplitude=39.052036 duration=675.9816
DurationFilter: Too short of a duration: duration=104.82794
Composer: Produced note# 59having frequency=862.323 amplitude=8.99058 duration=652.6327
Amplification: Amplified from old amplitude=64.55785 to new amplitude=54.557854
Amplification: Amplified from old amplitude=86.193344 to new amplitude=76.193344
Amplification: Amplified from old amplitude=41.344822 to new amplitude=31.344822
Amplification: Amplified from old amplitude=40.453274 to new amplitude=30.453274
Amplification: Amplified from old amplitude=66.852844 to new amplitude=56.852844
Amplification: Amplified from old amplitude=83.12224 to new amplitude=73.12224
Amplification: Amplified from old amplitude=58.985485 to new amplitude=48.985485
Amplification: Amplified from old amplitude=84.15711 to new amplitude=74.15711
Composer: Produced note# 60having frequency=328.10223 amplitude=77.258316 duration=908.0532
Composer: Produced note# 61having frequency=511.9685 amplitude=41.639606 duration=252.99513
Composer: Produced note# 62having frequency=723.863 amplitude=22.936375 duration=788.9393
Composer: Produced note# 63having frequency=538.3579 amplitude=16.10883 duration=792.2601
Composer: Produced note# 64having frequency=544.12225 amplitude=7.35907 duration=906.1454
Composer: Produced note# 65having frequency=786.1776 amplitude=43.565517 duration=972.4897
Composer: Produced note# 66having frequency=686.0363 amplitude=6.0729685 duration=812.7472
Composer: Produced note# 67having frequency=282.24927 amplitude=91.99441 duration=223.16533
Composer: Produced note# 68having frequency=110.85701 amplitude=84.445816 duration=140.59842
Composer: Produced note# 69having frequency=258.21924 amplitude=35.10327 duration=662.08606
Composer: Produced note# 70having frequency=794.1068 amplitude=7.4683785 duration=498.9694
DurationFilter: Too short of a duration: duration=123.71141
Composer: Produced note# 71having frequency=875.6661 amplitude=77.62211 duration=977.06824
Composer: Produced note# 72having frequency=464.09976 amplitude=74.309204 duration=78.739105
Composer: Produced note# 73having frequency=454.76996 amplitude=80.8282 duration=333.05508
Composer: Produced note# 74having frequency=773.2856 amplitude=16.974705 duration=819.5818
Composer: Produced note# 75having frequency=460.07794 amplitude=55.814583 duration=840.21826
Composer: Produced note# 76having frequency=966.68567 amplitude=30.37998 duration=142.03143
Composer: Produced note# 77having frequency=881.90826 amplitude=57.912098 duration=967.9501
Composer: Produced note# 78having frequency=38.78552 amplitude=33.509552 duration=792.9497
Composer: Produced note# 79having frequency=280.2461 amplitude=42.43869 duration=914.451
Composer: Produced note# 80having frequency=380.13263 amplitude=24.956 duration=640.4429
Composer: Produced note# 81having frequency=851.921 amplitude=90.57951 duration=683.0918
Composer: Produced note# 82having frequency=170.29631 amplitude=13.214988 duration=308.65842
Composer: Produced note# 83having frequency=608.3211 amplitude=65.47325 duration=654.0202
Composer: Produced note# 84having frequency=143.67479 amplitude=51.208645 duration=465.94077
Composer: Produced note# 85having frequency=790.2256 amplitude=58.594193 duration=489.7137
Composer: Produced note# 86having frequency=593.93616 amplitude=97.41261 duration=128.95375
Composer: Produced note# 87having frequency=544.9301 amplitude=83.71466 duration=759.1718
NoiseFilter: Accepted Note: frequency=798.9322 amplitude=81.968956 duration=815.7282
NoiseFilter: Accepted Note: frequency=172.87129 amplitude=66.32643 duration=932.2675
NoiseFilter: Too quiet or too loud: volume=-9.115217
NoiseFilter: Accepted Note: frequency=307.14374 amplitude=78.77671 duration=73.36348
NoiseFilter: Too quiet or too loud: volume=-3.7762794
NoiseFilter: Accepted Note: frequency=58.478355 amplitude=70.0528 duration=593.9475
NoiseFilter: Too quiet or too loud: volume=-8.566413
NoiseFilter: Accepted Note: frequency=685.0128 amplitude=46.73564 duration=0.21588802
NoiseFilter: Accepted Note: frequency=831.87103 amplitude=56.901436 duration=94.127594
NoiseFilter: Accepted Note: frequency=604.4891 amplitude=57.243317 duration=881.0994
NoiseFilter: Too quiet or too loud: volume=14.216621
Composer: Produced note# 88having frequency=537.83496 amplitude=0.40614605 duration=599.9628
Amplification: Amplified from old amplitude=15.67809 to new amplitude=5.67809
Amplification: Amplified from old amplitude=79.987625 to new amplitude=69.987625
Amplification: Amplified from old amplitude=16.26916 to new amplitude=6.2691593
DurationFilter: Accepted Note: frequency=798.9322 amplitude=81.968956 duration=815.7282
Amplification: Amplified from old amplitude=8.99058 to new amplitude=-1.0094204
Amplification: Amplified from old amplitude=77.258316 to new amplitude=67.258316
DurationFilter: Accepted Note: frequency=172.87129 amplitude=66.32643 duration=932.2675
NoiseFilter: Accepted Note: frequency=808.98755 amplitude=59.56861 duration=380.84067
NoiseFilter: Accepted Note: frequency=651.5937 amplitude=56.805855 duration=675.21246
NoiseFilter: Too quiet or too loud: volume=20.356657
NoiseFilter: Accepted Note: frequency=288.55127 amplitude=71.43971 duration=384.35175
NoiseFilter: Too quiet or too loud: volume=10.502371
NoiseFilter: Too quiet or too loud: volume=-4.5221004
NoiseFilter: Too quiet or too loud: volume=-0.67128277
NoiseFilter: Accepted Note: frequency=313.19858 amplitude=43.536076 duration=413.47565
Amplification: Amplified from old amplitude=41.639606 to new amplitude=31.639606
Amplification: Amplified from old amplitude=22.936375 to new amplitude=12.936375
Amplification: Amplified from old amplitude=16.10883 to new amplitude=6.1088295
Amplification: Amplified from old amplitude=7.35907 to new amplitude=-2.6409302
Amplification: Amplified from old amplitude=43.565517 to new amplitude=33.565517
Amplification: Amplified from old amplitude=6.0729685 to new amplitude=-3.9270315
Amplification: Amplified from old amplitude=91.99441 to new amplitude=81.99441
Amplification: Amplified from old amplitude=84.445816 to new amplitude=74.445816
Amplification: Amplified from old amplitude=35.10327 to new amplitude=25.103271
Amplification: Amplified from old amplitude=7.4683785 to new amplitude=-2.5316215
Amplification: Amplified from old amplitude=77.62211 to new amplitude=67.62211
Amplification: Amplified from old amplitude=74.309204 to new amplitude=64.309204
Amplification: Amplified from old amplitude=80.8282 to new amplitude=70.8282
Amplification: Amplified from old amplitude=16.974705 to new amplitude=6.9747047
Amplification: Amplified from old amplitude=55.814583 to new amplitude=45.814583
Amplification: Amplified from old amplitude=30.37998 to new amplitude=20.37998
Amplification: Amplified from old amplitude=57.912098 to new amplitude=47.912098
Amplification: Amplified from old amplitude=33.509552 to new amplitude=23.509552
Amplification: Amplified from old amplitude=42.43869 to new amplitude=32.43869
Amplification: Amplified from old amplitude=24.956 to new amplitude=14.955999
Amplification: Amplified from old amplitude=90.57951 to new amplitude=80.57951
Amplification: Amplified from old amplitude=13.214988 to new amplitude=3.2149878
Amplification: Amplified from old amplitude=65.47325 to new amplitude=55.47325
Amplification: Amplified from old amplitude=51.208645 to new amplitude=41.208645
Amplification: Amplified from old amplitude=58.594193 to new amplitude=48.594193
Amplification: Amplified from old amplitude=97.41261 to new amplitude=87.41261
Amplification: Amplified from old amplitude=83.71466 to new amplitude=73.71466
NoiseFilter: Too quiet or too loud: volume=14.196726
NoiseFilter: Accepted Note: frequency=435.5133 amplitude=54.557854 duration=628.08746
NoiseFilter: Accepted Note: frequency=371.16992 amplitude=76.193344 duration=880.9477
DurationFilter: Too short of a duration: duration=73.36348
NoiseFilter: Accepted Note: frequency=876.83466 amplitude=31.344822 duration=898.46564
DurationFilter: Accepted Note: frequency=58.478355 amplitude=70.0528 duration=593.9475
DurationFilter: Too short of a duration: duration=0.21588802
DurationFilter: Too short of a duration: duration=94.127594
NoiseFilter: Accepted Note: frequency=469.01453 amplitude=30.453274 duration=987.1203
DurationFilter: Accepted Note: frequency=604.4891 amplitude=57.243317 duration=881.0994
DurationFilter: Too short of a duration: duration=380.84067
NoiseFilter: Accepted Note: frequency=654.11084 amplitude=56.852844 duration=95.64787
DurationFilter: Accepted Note: frequency=651.5937 amplitude=56.805855 duration=675.21246
DurationFilter: Too short of a duration: duration=384.35175
NoiseFilter: Accepted Note: frequency=368.88947 amplitude=73.12224 duration=685.7148
DurationFilter: Too short of a duration: duration=413.47565
NoiseFilter: Accepted Note: frequency=983.0747 amplitude=48.985485 duration=403.13666
DurationFilter: Accepted Note: frequency=435.5133 amplitude=54.557854 duration=628.08746
NoiseFilter: Accepted Note: frequency=278.03864 amplitude=74.15711 duration=56.507587
DurationFilter: Accepted Note: frequency=371.16992 amplitude=76.193344 duration=880.9477
DurationFilter: Accepted Note: frequency=876.83466 amplitude=31.344822 duration=898.46564
NoiseFilter: Too quiet or too loud: volume=5.67809
DurationFilter: Accepted Note: frequency=469.01453 amplitude=30.453274 duration=987.1203
DurationFilter: Too short of a duration: duration=95.64787
NoiseFilter: Accepted Note: frequency=560.125 amplitude=69.987625 duration=253.63766
NoiseFilter: Too quiet or too loud: volume=6.2691593
NoiseFilter: Too quiet or too loud: volume=-1.0094204
DurationFilter: Accepted Note: frequency=368.88947 amplitude=73.12224 duration=685.7148
DurationFilter: Too short of a duration: duration=403.13666
DurationFilter: Too short of a duration: duration=56.507587
NoiseFilter: Accepted Note: frequency=328.10223 amplitude=67.258316 duration=908.0532
DurationFilter: Too short of a duration: duration=253.63766
NoiseFilter: Accepted Note: frequency=511.9685 amplitude=31.639606 duration=252.99513
NoiseFilter: Too quiet or too loud: volume=12.936375
DurationFilter: Accepted Note: frequency=328.10223 amplitude=67.258316 duration=908.0532
DurationFilter: Too short of a duration: duration=252.99513
NoiseFilter: Too quiet or too loud: volume=6.1088295
NoiseFilter: Too quiet or too loud: volume=-2.6409302
NoiseFilter: Accepted Note: frequency=786.1776 amplitude=33.565517 duration=972.4897
NoiseFilter: Too quiet or too loud: volume=-3.9270315
DurationFilter: Accepted Note: frequency=786.1776 amplitude=33.565517 duration=972.4897
NoiseFilter: Accepted Note: frequency=282.24927 amplitude=81.99441 duration=223.16533
NoiseFilter: Accepted Note: frequency=110.85701 amplitude=74.445816 duration=140.59842
NoiseFilter: Too quiet or too loud: volume=25.103271
NoiseFilter: Too quiet or too loud: volume=-2.5316215
NoiseFilter: Accepted Note: frequency=875.6661 amplitude=67.62211 duration=977.06824
NoiseFilter: Accepted Note: frequency=464.09976 amplitude=64.309204 duration=78.739105
NoiseFilter: Accepted Note: frequency=454.76996 amplitude=70.8282 duration=333.05508
NoiseFilter: Too quiet or too loud: volume=6.9747047
NoiseFilter: Accepted Note: frequency=460.07794 amplitude=45.814583 duration=840.21826
NoiseFilter: Too quiet or too loud: volume=20.37998
NoiseFilter: Accepted Note: frequency=881.90826 amplitude=47.912098 duration=967.9501
NoiseFilter: Too quiet or too loud: volume=23.509552
NoiseFilter: Accepted Note: frequency=280.2461 amplitude=32.43869 duration=914.451
NoiseFilter: Too quiet or too loud: volume=14.955999
DurationFilter: Too short of a duration: duration=223.16533
DurationFilter: Too short of a duration: duration=140.59842
NoiseFilter: Accepted Note: frequency=851.921 amplitude=80.57951 duration=683.0918
NoiseFilter: Too quiet or too loud: volume=3.2149878
DurationFilter: Accepted Note: frequency=875.6661 amplitude=67.62211 duration=977.06824
DurationFilter: Too short of a duration: duration=78.739105
NoiseFilter: Accepted Note: frequency=608.3211 amplitude=55.47325 duration=654.0202
NoiseFilter: Accepted Note: frequency=143.67479 amplitude=41.208645 duration=465.94077
DurationFilter: Too short of a duration: duration=333.05508
NoiseFilter: Accepted Note: frequency=790.2256 amplitude=48.594193 duration=489.7137
DurationFilter: Accepted Note: frequency=460.07794 amplitude=45.814583 duration=840.21826
NoiseFilter: Accepted Note: frequency=593.93616 amplitude=87.41261 duration=128.95375
DurationFilter: Accepted Note: frequency=881.90826 amplitude=47.912098 duration=967.9501
NoiseFilter: Accepted Note: frequency=544.9301 amplitude=73.71466 duration=759.1718
DurationFilter: Accepted Note: frequency=280.2461 amplitude=32.43869 duration=914.451
DurationFilter: Accepted Note: frequency=851.921 amplitude=80.57951 duration=683.0918
DurationFilter: Accepted Note: frequency=608.3211 amplitude=55.47325 duration=654.0202
DurationFilter: Too short of a duration: duration=465.94077
DurationFilter: Too short of a duration: duration=489.7137
DurationFilter: Too short of a duration: duration=128.95375
DurationFilter: Accepted Note: frequency=544.9301 amplitude=73.71466 duration=759.1718
Amplification: Amplified from old amplitude=0.40614605 to new amplitude=-9.593854
Composer: Produced note# 89having frequency=325.45258 amplitude=82.9946 duration=677.0552
NoiseFilter: Too quiet or too loud: volume=-9.593854
Amplification: Amplified from old amplitude=82.9946 to new amplitude=72.9946
Composer: Produced note# 90having frequency=797.9891 amplitude=41.42664 duration=611.88055
Amplification: Amplified from old amplitude=41.42664 to new amplitude=31.42664
NoiseFilter: Accepted Note: frequency=325.45258 amplitude=72.9946 duration=677.0552
NoiseFilter: Accepted Note: frequency=797.9891 amplitude=31.42664 duration=611.88055
DurationFilter: Accepted Note: frequency=325.45258 amplitude=72.9946 duration=677.0552
Composer: Produced note# 91having frequency=941.7192 amplitude=92.08498 duration=207.24785
Amplification: Amplified from old amplitude=92.08498 to new amplitude=82.08498
DurationFilter: Accepted Note: frequency=797.9891 amplitude=31.42664 duration=611.88055
Composer: Produced note# 92having frequency=656.33246 amplitude=35.711647 duration=4.993856
NoiseFilter: The note is suspicious: amplitude=82.08498 predecessor's amplitude=31.42664
Amplification: Amplified from old amplitude=35.711647 to new amplitude=25.711647
NoiseFilter: Too quiet or too loud: volume=25.711647
Composer: Produced note# 93having frequency=563.3105 amplitude=96.11157 duration=898.27014
Amplification: Amplified from old amplitude=96.11157 to new amplitude=86.11157
NoiseFilter: The note is suspicious: amplitude=86.11157 predecessor's amplitude=31.42664
Composer: Produced note# 94having frequency=557.6294 amplitude=71.94769 duration=318.40454
Amplification: Amplified from old amplitude=71.94769 to new amplitude=61.947693
NoiseFilter: Accepted Note: frequency=557.6294 amplitude=61.947693 duration=318.40454
DurationFilter: Too short of a duration: duration=318.40454
Composer: Produced note# 95having frequency=959.90454 amplitude=32.076836 duration=374.6932
Amplification: Amplified from old amplitude=32.076836 to new amplitude=22.076836
NoiseFilter: Too quiet or too loud: volume=22.076836
Composer: Produced note# 96having frequency=184.21405 amplitude=86.416145 duration=243.33662
Amplification: Amplified from old amplitude=86.416145 to new amplitude=76.416145
NoiseFilter: Accepted Note: frequency=184.21405 amplitude=76.416145 duration=243.33662
DurationFilter: Too short of a duration: duration=243.33662
Composer: Produced note# 97having frequency=834.6226 amplitude=36.998985 duration=535.00653
Amplification: Amplified from old amplitude=36.998985 to new amplitude=26.998985
NoiseFilter: Too quiet or too loud: volume=26.998985
Composer: Produced note# 98having frequency=808.2805 amplitude=28.265041 duration=336.84128
Amplification: Amplified from old amplitude=28.265041 to new amplitude=18.265041
NoiseFilter: Too quiet or too loud: volume=18.265041
Composer: Produced note# 99having frequency=940.83905 amplitude=32.84613 duration=859.9133
Shutting down Producer
Amplification: Amplified from old amplitude=32.84613 to new amplitude=22.84613
Shutting down Transformer
Shutting down Tester
Shutting down Tester
Playing: frequency=728.8361 amplitude=71.60767 duration=661.3551
Playing: frequency=643.0225 amplitude=34.42164 duration=654.08154
Playing: frequency=516.01294 amplitude=81.143036 duration=923.6866
Playing: frequency=278.74017 amplitude=72.79412 duration=789.2302
Playing: frequency=221.09676 amplitude=37.700344 duration=548.13745
Playing: frequency=720.12036 amplitude=61.94771 duration=503.30222
Playing: frequency=280.6212 amplitude=58.37787 duration=982.54956
Playing: frequency=192.63977 amplitude=39.052036 duration=675.9816
Playing: frequency=798.9322 amplitude=81.968956 duration=815.7282
Playing: frequency=172.87129 amplitude=66.32643 duration=932.2675
Playing: frequency=58.478355 amplitude=70.0528 duration=593.9475
Playing: frequency=604.4891 amplitude=57.243317 duration=881.0994
Playing: frequency=651.5937 amplitude=56.805855 duration=675.21246
Playing: frequency=435.5133 amplitude=54.557854 duration=628.08746
Playing: frequency=371.16992 amplitude=76.193344 duration=880.9477
Playing: frequency=876.83466 amplitude=31.344822 duration=898.46564
Playing: frequency=469.01453 amplitude=30.453274 duration=987.1203
Playing: frequency=368.88947 amplitude=73.12224 duration=685.7148
Playing: frequency=328.10223 amplitude=67.258316 duration=908.0532
Playing: frequency=786.1776 amplitude=33.565517 duration=972.4897
Playing: frequency=875.6661 amplitude=67.62211 duration=977.06824
Playing: frequency=460.07794 amplitude=45.814583 duration=840.21826
Playing: frequency=881.90826 amplitude=47.912098 duration=967.9501
Playing: frequency=280.2461 amplitude=32.43869 duration=914.451
Playing: frequency=851.921 amplitude=80.57951 duration=683.0918
Playing: frequency=608.3211 amplitude=55.47325 duration=654.0202
Playing: frequency=544.9301 amplitude=73.71466 duration=759.1718
Playing: frequency=325.45258 amplitude=72.9946 duration=677.0552
Playing: frequency=797.9891 amplitude=31.42664 duration=611.88055
Playing: frequency=940.83905 amplitude=22.84613 duration=859.9133
Shutting down Consumer
Stop dancing!! Music is over~~~
```


* [Airline Performance Analyzer](http://www.cs.sjsu.edu/faculty/pearce/modules/projects/streams/index.htm)
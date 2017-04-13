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
  * Digital Composer: Generates a random sequence of <= 100 notes.


Output for GarBage Band when run for producing Notes on a 'Piano':
```
Starting the GarBage Band application... start dancing.. PepePls!
Started Producer
Started Transformer
Started Tester
Started Consumer
Composer: Produced note# 0having frequency=85.482956 amplitude=94.50554 duration=60.55999
Composer: Produced note# 1having frequency=869.30493 amplitude=96.22232 duration=811.3139
Composer: Produced note# 2having frequency=500.16284 amplitude=52.65851 duration=979.1076
Amplification: Amplified from old amplitude=94.50554 to new amplitude=84.50554
Composer: Produced note# 3having frequency=863.0429 amplitude=56.692444 duration=550.13715
Amplification: Amplified from old amplitude=96.22232 to new amplitude=86.22232
Filter: Too short of a duration: duration=60.55999
Amplification: Amplified from old amplitude=52.65851 to new amplitude=42.65851
Composer: Produced note# 4having frequency=75.717865 amplitude=9.422999 duration=431.39737
Amplification: Amplified from old amplitude=56.692444 to new amplitude=46.692444
Composer: Produced note# 5having frequency=465.69467 amplitude=46.594543 duration=222.2799
Amplification: Amplified from old amplitude=9.422999 to new amplitude=-0.5770006
Composer: Produced note# 6having frequency=4.881561 amplitude=8.855963 duration=14.80794
Amplification: Amplified from old amplitude=46.594543 to new amplitude=36.594543
Composer: Produced note# 7having frequency=91.95179 amplitude=61.639416 duration=106.689575
Amplification: Amplified from old amplitude=8.855963 to new amplitude=-1.1440372
Composer: Produced note# 8having frequency=928.17786 amplitude=95.01767 duration=117.40983
Amplification: Amplified from old amplitude=61.639416 to new amplitude=51.639416
Amplification: Amplified from old amplitude=95.01767 to new amplitude=85.01767
Composer: Produced note# 9having frequency=723.31305 amplitude=77.896675 duration=193.85094
Amplification: Amplified from old amplitude=77.896675 to new amplitude=67.896675
Composer: Produced note# 10having frequency=451.477 amplitude=5.3450823 duration=12.561202
Amplification: Amplified from old amplitude=5.3450823 to new amplitude=-4.6549177
Composer: Produced note# 11having frequency=554.71814 amplitude=4.178339 duration=474.50186
Amplification: Amplified from old amplitude=4.178339 to new amplitude=-5.821661
Composer: Produced note# 12having frequency=556.13635 amplitude=29.852068 duration=438.81268
Amplification: Amplified from old amplitude=29.852068 to new amplitude=19.852068
Composer: Produced note# 13having frequency=624.5248 amplitude=73.0385 duration=909.5008
Amplification: Amplified from old amplitude=73.0385 to new amplitude=63.038498
Composer: Produced note# 14having frequency=277.22006 amplitude=69.547935 duration=988.06476
Composer: Produced note# 15having frequency=376.73276 amplitude=5.9805098 duration=78.98778
Amplification: Amplified from old amplitude=69.547935 to new amplitude=59.547935
Composer: Produced note# 16having frequency=104.71755 amplitude=67.15449 duration=276.1782
Amplification: Amplified from old amplitude=5.9805098 to new amplitude=-4.0194902
Composer: Produced note# 17having frequency=478.80423 amplitude=5.5795135 duration=476.37994
Amplification: Amplified from old amplitude=67.15449 to new amplitude=57.154488
Composer: Produced note# 18having frequency=187.91872 amplitude=17.459208 duration=788.4428
Amplification: Amplified from old amplitude=5.5795135 to new amplitude=-4.4204865
Composer: Produced note# 19having frequency=374.2801 amplitude=4.343504 duration=879.797
Amplification: Amplified from old amplitude=17.459208 to new amplitude=7.4592075
Composer: Produced note# 20having frequency=313.97897 amplitude=10.683542 duration=335.7856
Amplification: Amplified from old amplitude=4.343504 to new amplitude=-5.656496
Amplification: Amplified from old amplitude=10.683542 to new amplitude=0.68354225
Composer: Produced note# 21having frequency=364.5544 amplitude=8.057136 duration=511.20102
Amplification: Amplified from old amplitude=8.057136 to new amplitude=-1.9428644
Composer: Produced note# 22having frequency=304.34042 amplitude=56.844086 duration=936.15063
Composer: Produced note# 23having frequency=976.3782 amplitude=66.10886 duration=192.97868
Amplification: Amplified from old amplitude=56.844086 to new amplitude=46.844086
Composer: Produced note# 24having frequency=246.06555 amplitude=21.645647 duration=859.13257
Amplification: Amplified from old amplitude=66.10886 to new amplitude=56.108856
Composer: Produced note# 25having frequency=248.3952 amplitude=90.873665 duration=216.72243
Amplification: Amplified from old amplitude=21.645647 to new amplitude=11.645647
Amplification: Amplified from old amplitude=90.873665 to new amplitude=80.873665
Composer: Produced note# 26having frequency=38.432957 amplitude=67.52738 duration=448.1545
Amplification: Amplified from old amplitude=67.52738 to new amplitude=57.52738
Composer: Produced note# 27having frequency=350.59613 amplitude=26.463121 duration=665.884
Amplification: Amplified from old amplitude=26.463121 to new amplitude=16.463121
Composer: Produced note# 28having frequency=265.8592 amplitude=91.33148 duration=268.4812
Amplification: Amplified from old amplitude=91.33148 to new amplitude=81.33148
Composer: Produced note# 29having frequency=412.6777 amplitude=17.932987 duration=429.72034
Amplification: Amplified from old amplitude=17.932987 to new amplitude=7.932987
Composer: Produced note# 30having frequency=646.8237 amplitude=60.35777 duration=178.8457
Amplification: Amplified from old amplitude=60.35777 to new amplitude=50.35777
Composer: Produced note# 31having frequency=179.70688 amplitude=49.316372 duration=839.4165
Amplification: Amplified from old amplitude=49.316372 to new amplitude=39.316372
Composer: Produced note# 32having frequency=264.08075 amplitude=56.695045 duration=284.74463
Amplification: Amplified from old amplitude=56.695045 to new amplitude=46.695045
Composer: Produced note# 33having frequency=927.9965 amplitude=84.95198 duration=575.823
Amplification: Amplified from old amplitude=84.95198 to new amplitude=74.95198
Composer: Produced note# 34having frequency=215.6369 amplitude=12.835354 duration=427.86713
Composer: Produced note# 35having frequency=48.13832 amplitude=78.73758 duration=640.89557
Amplification: Amplified from old amplitude=12.835354 to new amplitude=2.8353539
Composer: Produced note# 36having frequency=414.4239 amplitude=60.46092 duration=648.33746
Amplification: Amplified from old amplitude=78.73758 to new amplitude=68.73758
Composer: Produced note# 37having frequency=676.3232 amplitude=33.161514 duration=788.48114
Amplification: Amplified from old amplitude=60.46092 to new amplitude=50.46092
Composer: Produced note# 38having frequency=15.03849 amplitude=17.597412 duration=238.96301
Amplification: Amplified from old amplitude=33.161514 to new amplitude=23.161514
Composer: Produced note# 39having frequency=123.961624 amplitude=93.510445 duration=470.52032
Amplification: Amplified from old amplitude=17.597412 to new amplitude=7.597412
Composer: Produced note# 40having frequency=957.2309 amplitude=32.845127 duration=262.9783
Amplification: Amplified from old amplitude=93.510445 to new amplitude=83.510445
Composer: Produced note# 41having frequency=422.98334 amplitude=10.069686 duration=188.48026
Amplification: Amplified from old amplitude=32.845127 to new amplitude=22.845127
Composer: Produced note# 42having frequency=459.4988 amplitude=58.787315 duration=441.8876
Amplification: Amplified from old amplitude=10.069686 to new amplitude=0.069685936
Composer: Produced note# 43having frequency=40.2506 amplitude=25.901525 duration=580.22125
Amplification: Amplified from old amplitude=58.787315 to new amplitude=48.787315
Composer: Produced note# 44having frequency=61.015247 amplitude=13.389778 duration=282.85654
Amplification: Amplified from old amplitude=25.901525 to new amplitude=15.9015255
Composer: Produced note# 45having frequency=565.1918 amplitude=63.20504 duration=838.87274
Amplification: Amplified from old amplitude=13.389778 to new amplitude=3.3897781
Composer: Produced note# 46having frequency=866.2133 amplitude=66.147 duration=189.56525
Filter: Accepted Note: frequency=869.30493 amplitude=86.22232 duration=811.3139
Composer: Produced note# 47having frequency=831.6015 amplitude=7.3551774 duration=559.8188
Composer: Produced note# 48having frequency=826.8911 amplitude=44.575703 duration=303.16055
Playing: frequency=869.30493 amplitude=86.22232 duration=811.3139
Composer: Produced note# 49having frequency=472.54156 amplitude=83.10888 duration=152.32808
Composer: Produced note# 50having frequency=69.981575 amplitude=41.41091 duration=190.19968
Composer: Produced note# 51having frequency=70.80716 amplitude=69.013374 duration=774.778
Composer: Produced note# 52having frequency=357.18805 amplitude=42.2597 duration=543.22876
Composer: Produced note# 53having frequency=373.02905 amplitude=28.494476 duration=425.93765
Composer: Produced note# 54having frequency=183.13193 amplitude=96.90762 duration=365.6555
Composer: Produced note# 55having frequency=43.80345 amplitude=43.320335 duration=953.63745
Composer: Produced note# 56having frequency=396.6735 amplitude=63.228355 duration=278.72147
Amplification: Amplified from old amplitude=63.20504 to new amplitude=53.20504
Composer: Produced note# 57having frequency=118.3089 amplitude=47.236465 duration=430.98462
Composer: Produced note# 58having frequency=726.078 amplitude=33.511837 duration=305.66513
Composer: Produced note# 59having frequency=744.6135 amplitude=24.182951 duration=121.6405
Filter: Accepted Note: frequency=500.16284 amplitude=42.65851 duration=979.1076
Composer: Produced note# 60having frequency=741.4412 amplitude=57.255466 duration=21.9872
Amplification: Amplified from old amplitude=66.147 to new amplitude=56.147003
Composer: Produced note# 61having frequency=867.89014 amplitude=8.946103 duration=302.74088
Amplification: Amplified from old amplitude=7.3551774 to new amplitude=-2.6448226
Composer: Produced note# 62having frequency=796.1982 amplitude=57.498394 duration=254.29195
Amplification: Amplified from old amplitude=44.575703 to new amplitude=34.575703
Composer: Produced note# 63having frequency=493.37292 amplitude=96.49697 duration=951.74536
Filter: Accepted Note: frequency=863.0429 amplitude=46.692444 duration=550.13715
Composer: Produced note# 64having frequency=479.12192 amplitude=27.772856 duration=767.33344
Amplification: Amplified from old amplitude=83.10888 to new amplitude=73.10888
Composer: Produced note# 65having frequency=933.2848 amplitude=75.92123 duration=694.89264
Amplification: Amplified from old amplitude=41.41091 to new amplitude=31.410912
Filter: Too quiet or too loud: volume=-0.5770006
Filter: Too short of a duration: duration=222.2799
Filter: Frequency is inaudible: frequency=4.881561
Filter: Too short of a duration: duration=106.689575
Filter: Too short of a duration: duration=117.40983
Amplification: Amplified from old amplitude=69.013374 to new amplitude=59.013374
Composer: Produced note# 66having frequency=445.25592 amplitude=20.02179 duration=228.40672
Amplification: Amplified from old amplitude=42.2597 to new amplitude=32.2597
Composer: Produced note# 67having frequency=113.90448 amplitude=60.425587 duration=518.2271
Amplification: Amplified from old amplitude=28.494476 to new amplitude=18.494476
Composer: Produced note# 68having frequency=960.26373 amplitude=16.758627 duration=311.62173
Filter: Too short of a duration: duration=193.85094
Composer: Produced note# 69having frequency=732.4991 amplitude=39.728363 duration=9.029865
Amplification: Amplified from old amplitude=96.90762 to new amplitude=86.90762
Composer: Produced note# 70having frequency=348.39053 amplitude=79.67759 duration=402.06015
Amplification: Amplified from old amplitude=43.320335 to new amplitude=33.320335
Filter: Too quiet or too loud: volume=-4.6549177
Amplification: Amplified from old amplitude=63.228355 to new amplitude=53.228355
Composer: Produced note# 71having frequency=21.561146 amplitude=44.980846 duration=69.06211
Amplification: Amplified from old amplitude=47.236465 to new amplitude=37.236465
Composer: Produced note# 72having frequency=539.74097 amplitude=41.67846 duration=392.64297
Amplification: Amplified from old amplitude=33.511837 to new amplitude=23.511837
Composer: Produced note# 73having frequency=864.7728 amplitude=9.089136 duration=750.08264
Filter: Too quiet or too loud: volume=-5.821661
Composer: Produced note# 74having frequency=85.500656 amplitude=27.246565 duration=322.12286
Amplification: Amplified from old amplitude=24.182951 to new amplitude=14.182951
Amplification: Amplified from old amplitude=57.255466 to new amplitude=47.255466
Filter: Too quiet or too loud: volume=19.852068
Amplification: Amplified from old amplitude=8.946103 to new amplitude=-1.0538969
Amplification: Amplified from old amplitude=57.498394 to new amplitude=47.498394
Amplification: Amplified from old amplitude=96.49697 to new amplitude=86.49697
Amplification: Amplified from old amplitude=27.772856 to new amplitude=17.772856
Composer: Produced note# 75having frequency=377.5354 amplitude=44.416855 duration=951.09064
Composer: Produced note# 76having frequency=635.71497 amplitude=29.144596 duration=831.8658
Composer: Produced note# 77having frequency=182.41006 amplitude=14.963776 duration=960.92267
Composer: Produced note# 78having frequency=672.60974 amplitude=11.120176 duration=609.21216
Composer: Produced note# 79having frequency=305.83273 amplitude=40.159164 duration=197.11983
Composer: Produced note# 80having frequency=872.99097 amplitude=32.89141 duration=825.11847
Composer: Produced note# 81having frequency=562.7626 amplitude=34.673603 duration=872.7942
Composer: Produced note# 82having frequency=866.5721 amplitude=47.569622 duration=398.9029
Composer: Produced note# 83having frequency=715.30133 amplitude=47.011513 duration=454.77295
Composer: Produced note# 84having frequency=901.0523 amplitude=17.9294 duration=334.19162
Composer: Produced note# 85having frequency=552.00214 amplitude=66.33733 duration=246.94473
Composer: Produced note# 86having frequency=922.2391 amplitude=48.500206 duration=222.42761
Composer: Produced note# 87having frequency=557.5133 amplitude=24.024088 duration=577.2554
Amplification: Amplified from old amplitude=75.92123 to new amplitude=65.92123
Filter: Accepted Note: frequency=624.5248 amplitude=63.038498 duration=909.5008
Amplification: Amplified from old amplitude=20.02179 to new amplitude=10.02179
Amplification: Amplified from old amplitude=60.425587 to new amplitude=50.425587
Amplification: Amplified from old amplitude=16.758627 to new amplitude=6.758627
Composer: Produced note# 88having frequency=157.43953 amplitude=22.561014 duration=370.58865
Amplification: Amplified from old amplitude=39.728363 to new amplitude=29.728363
Filter: Accepted Note: frequency=277.22006 amplitude=59.547935 duration=988.06476
Amplification: Amplified from old amplitude=79.67759 to new amplitude=69.67759
Composer: Produced note# 89having frequency=132.69519 amplitude=84.066635 duration=303.23547
Amplification: Amplified from old amplitude=44.980846 to new amplitude=34.980846
Composer: Produced note# 90having frequency=597.5103 amplitude=70.998085 duration=169.52115
Filter: Too quiet or too loud: volume=-4.0194902
Composer: Produced note# 91having frequency=970.38135 amplitude=59.469204 duration=784.883
Amplification: Amplified from old amplitude=41.67846 to new amplitude=31.67846
Composer: Produced note# 92having frequency=633.7536 amplitude=6.5230846 duration=323.99475
Filter: Too short of a duration: duration=276.1782
Composer: Produced note# 93having frequency=757.59705 amplitude=53.326374 duration=675.2503
Filter: Too quiet or too loud: volume=-4.4204865
Composer: Produced note# 94having frequency=304.8511 amplitude=47.326893 duration=823.91
Amplification: Amplified from old amplitude=9.089136 to new amplitude=-0.9108639
Composer: Produced note# 95having frequency=535.3248 amplitude=22.076702 duration=336.6453
Filter: Too quiet or too loud: volume=7.4592075
Composer: Produced note# 96having frequency=509.68115 amplitude=82.22118 duration=800.3156
Amplification: Amplified from old amplitude=27.246565 to new amplitude=17.246565
Composer: Produced note# 97having frequency=109.51793 amplitude=14.234567 duration=92.65202
Filter: Too quiet or too loud: volume=-5.656496
Filter: Too quiet or too loud: volume=0.68354225
Filter: Too quiet or too loud: volume=-1.9428644
Composer: Produced note# 98having frequency=863.98773 amplitude=87.7084 duration=413.87857
Composer: Produced note# 99having frequency=61.243294 amplitude=14.67728 duration=280.0672
Shutting down Producer
Filter: Accepted Note: frequency=304.34042 amplitude=46.844086 duration=936.15063
Amplification: Amplified from old amplitude=44.416855 to new amplitude=34.416855
Filter: Too short of a duration: duration=192.97868
Amplification: Amplified from old amplitude=29.144596 to new amplitude=19.144596
Filter: Too quiet or too loud: volume=11.645647
Amplification: Amplified from old amplitude=14.963776 to new amplitude=4.9637756
Filter: Too short of a duration: duration=216.72243
Amplification: Amplified from old amplitude=11.120176 to new amplitude=1.1201763
Filter: Too short of a duration: duration=448.1545
Amplification: Amplified from old amplitude=40.159164 to new amplitude=30.159164
Filter: Too quiet or too loud: volume=16.463121
Amplification: Amplified from old amplitude=32.89141 to new amplitude=22.89141
Filter: Too short of a duration: duration=268.4812
Filter: Too quiet or too loud: volume=7.932987
Filter: Too short of a duration: duration=178.8457
Amplification: Amplified from old amplitude=34.673603 to new amplitude=24.673603
Amplification: Amplified from old amplitude=47.569622 to new amplitude=37.569622
Amplification: Amplified from old amplitude=47.011513 to new amplitude=37.011513
Amplification: Amplified from old amplitude=17.9294 to new amplitude=7.9293995
Amplification: Amplified from old amplitude=66.33733 to new amplitude=56.337334
Amplification: Amplified from old amplitude=48.500206 to new amplitude=38.500206
Amplification: Amplified from old amplitude=24.024088 to new amplitude=14.024088
Amplification: Amplified from old amplitude=22.561014 to new amplitude=12.561014
Amplification: Amplified from old amplitude=84.066635 to new amplitude=74.066635
Amplification: Amplified from old amplitude=70.998085 to new amplitude=60.998085
Amplification: Amplified from old amplitude=59.469204 to new amplitude=49.469204
Amplification: Amplified from old amplitude=6.5230846 to new amplitude=-3.4769154
Amplification: Amplified from old amplitude=53.326374 to new amplitude=43.326374
Amplification: Amplified from old amplitude=47.326893 to new amplitude=37.326893
Amplification: Amplified from old amplitude=22.076702 to new amplitude=12.076702
Amplification: Amplified from old amplitude=82.22118 to new amplitude=72.22118
Amplification: Amplified from old amplitude=14.234567 to new amplitude=4.2345667
Amplification: Amplified from old amplitude=87.7084 to new amplitude=77.7084
Amplification: Amplified from old amplitude=14.67728 to new amplitude=4.6772804
Shutting down Transformer
Filter: Accepted Note: frequency=179.70688 amplitude=39.316372 duration=839.4165
Filter: Too short of a duration: duration=284.74463
Filter: Accepted Note: frequency=927.9965 amplitude=74.95198 duration=575.823
Filter: Too quiet or too loud: volume=2.8353539
Filter: Accepted Note: frequency=48.13832 amplitude=68.73758 duration=640.89557
Filter: Accepted Note: frequency=414.4239 amplitude=50.46092 duration=648.33746
Filter: Too quiet or too loud: volume=23.161514
Filter: Frequency is inaudible: frequency=15.03849
Filter: Too short of a duration: duration=470.52032
Filter: Too quiet or too loud: volume=22.845127
Filter: Too quiet or too loud: volume=0.069685936
Filter: Too short of a duration: duration=441.8876
Filter: Too quiet or too loud: volume=15.9015255
Filter: Too quiet or too loud: volume=3.3897781
Filter: Accepted Note: frequency=565.1918 amplitude=53.20504 duration=838.87274
Filter: Too short of a duration: duration=189.56525
Filter: Too quiet or too loud: volume=-2.6448226
Filter: Too short of a duration: duration=303.16055
Filter: Too short of a duration: duration=152.32808
Filter: Too short of a duration: duration=190.19968
Filter: Accepted Note: frequency=70.80716 amplitude=59.013374 duration=774.778
Filter: Accepted Note: frequency=357.18805 amplitude=32.2597 duration=543.22876
Filter: Too quiet or too loud: volume=18.494476
Filter: Too short of a duration: duration=365.6555
Filter: Accepted Note: frequency=43.80345 amplitude=33.320335 duration=953.63745
Filter: Too short of a duration: duration=278.72147
Filter: Too short of a duration: duration=430.98462
Filter: Too quiet or too loud: volume=23.511837
Filter: Too quiet or too loud: volume=14.182951
Filter: Too short of a duration: duration=21.9872
Filter: Too quiet or too loud: volume=-1.0538969
Filter: Too short of a duration: duration=254.29195
Filter: The note is suspicious: amplitude=86.49697 predecessor's amplitude=33.320335
Filter: Too quiet or too loud: volume=17.772856
Filter: Accepted Note: frequency=933.2848 amplitude=65.92123 duration=694.89264
Filter: Too quiet or too loud: volume=10.02179
Filter: Accepted Note: frequency=113.90448 amplitude=50.425587 duration=518.2271
Filter: Too quiet or too loud: volume=6.758627
Filter: Too quiet or too loud: volume=29.728363
Filter: Too short of a duration: duration=402.06015
Filter: Too short of a duration: duration=69.06211
Filter: Too short of a duration: duration=392.64297
Filter: Too quiet or too loud: volume=-0.9108639
Filter: Too quiet or too loud: volume=17.246565
Filter: Accepted Note: frequency=377.5354 amplitude=34.416855 duration=951.09064
Filter: Too quiet or too loud: volume=19.144596
Filter: Too quiet or too loud: volume=4.9637756
Filter: Too quiet or too loud: volume=1.1201763
Filter: Too short of a duration: duration=197.11983
Filter: Too quiet or too loud: volume=22.89141
Filter: Too quiet or too loud: volume=24.673603
Filter: Too short of a duration: duration=398.9029
Filter: Too short of a duration: duration=454.77295
Filter: Too quiet or too loud: volume=7.9293995
Filter: Too short of a duration: duration=246.94473
Filter: Too short of a duration: duration=222.42761
Filter: Too quiet or too loud: volume=14.024088
Filter: Too quiet or too loud: volume=12.561014
Filter: Too short of a duration: duration=303.23547
Filter: Too short of a duration: duration=169.52115
Filter: Accepted Note: frequency=970.38135 amplitude=49.469204 duration=784.883
Filter: Too quiet or too loud: volume=-3.4769154
Filter: Accepted Note: frequency=757.59705 amplitude=43.326374 duration=675.2503
Filter: Accepted Note: frequency=304.8511 amplitude=37.326893 duration=823.91
Filter: Too quiet or too loud: volume=12.076702
Filter: Accepted Note: frequency=509.68115 amplitude=72.22118 duration=800.3156
Filter: Too quiet or too loud: volume=4.2345667
Filter: Too short of a duration: duration=413.87857
Shutting down Tester
Playing: frequency=500.16284 amplitude=42.65851 duration=979.1076
Playing: frequency=863.0429 amplitude=46.692444 duration=550.13715
Playing: frequency=624.5248 amplitude=63.038498 duration=909.5008
Playing: frequency=277.22006 amplitude=59.547935 duration=988.06476
Playing: frequency=304.34042 amplitude=46.844086 duration=936.15063
Playing: frequency=179.70688 amplitude=39.316372 duration=839.4165
Playing: frequency=927.9965 amplitude=74.95198 duration=575.823
Playing: frequency=48.13832 amplitude=68.73758 duration=640.89557
Playing: frequency=414.4239 amplitude=50.46092 duration=648.33746
Playing: frequency=565.1918 amplitude=53.20504 duration=838.87274
Playing: frequency=70.80716 amplitude=59.013374 duration=774.778
Playing: frequency=357.18805 amplitude=32.2597 duration=543.22876
Playing: frequency=43.80345 amplitude=33.320335 duration=953.63745
Playing: frequency=933.2848 amplitude=65.92123 duration=694.89264
Playing: frequency=113.90448 amplitude=50.425587 duration=518.2271
Playing: frequency=377.5354 amplitude=34.416855 duration=951.09064
Playing: frequency=970.38135 amplitude=49.469204 duration=784.883
Playing: frequency=757.59705 amplitude=43.326374 duration=675.2503
Playing: frequency=304.8511 amplitude=37.326893 duration=823.91
Playing: frequency=509.68115 amplitude=72.22118 duration=800.3156
Playing: frequency=61.243294 amplitude=4.6772804 duration=280.0672
Shutting down Consumer
```


* [Airline Performance Analyzer](http://www.cs.sjsu.edu/faculty/pearce/modules/projects/streams/index.htm)
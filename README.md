# Software Engineering Project Starter Code

# CHOSEN COMPUTATION:
The chosen computation for the project is a Collatz Sequence. This sequence takes in a positive integer and then returns a string of numbers. The output is dependent on whether or not the previous number or original input is even or odd. If the number is even, it is divided by 2. If the number is odd, it is multiplied by 3 with the addition of 1. This sequence continues until the final output is 1.

# MULTI-THREADING:
![<Image showing project diagram with three APIs>](https://github.com/CPS353-Suny-New-Paltz/project-starter-code-burnsidk1/blob/main/API.png?raw=true)

Maximum thread pool size for multi-threading: 8 threads

# COMPUTE-COORDINATOR INTEGRATION BENCHMARK TEST:
a. Original: 181 ms, Cached: 22 ms, Improvement: 87.8%
b. Link to Benchmark Test: [`test/performance/ComputeCoordinatorIntegrationBenchmarkTest.java`](test/performance/ComputeCoordinatorIntegrationBenchmarkTest.java)
c. The issue with the previous version is that the computation component would have to make a calculation for every single input, regardless of whether or not the computation already happened. This meant that the coordinator was processing a large amount of unecessary job submission requests.
The fix to this issue was to implement caching (ConcurrentHashMap) for the computation component, so that when a computation occured, its result would be stored. Then, if the same input calculation was requested, there would be no need to compute the output again. This fix resulted in a 87.8% improvement in overall computation time. 
The benchmark was measured using a sample of 2,000 input integers with 20 of them being repeats.
d. Link to PR for the fix: https://github.com/CPS353-Suny-New-Paltz/project-starter-code-burnsidk1/pull/47

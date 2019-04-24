hadoop fs -rmr /hao/wordout
hadoop fs -rmr /hao/wordfinal
hadoop  -classic jar wordcount.jar WordCount -Dmapred.reduce.tasks=3 /hao/input /hao/wordfinal

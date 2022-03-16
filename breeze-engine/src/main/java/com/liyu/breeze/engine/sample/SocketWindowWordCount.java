//package com.liyu.breeze.engine.sample;
//
//import org.apache.flink.api.common.functions.FlatMapFunction;
//import org.apache.flink.api.common.functions.ReduceFunction;
//import org.apache.flink.streaming.api.datastream.DataStream;
//import org.apache.flink.streaming.api.datastream.DataStreamSource;
//import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
//import org.apache.flink.streaming.api.windowing.assigners.SlidingProcessingTimeWindows;
//import org.apache.flink.streaming.api.windowing.time.Time;
//import org.apache.flink.util.Collector;
//
///**
// * @author gleiyu
// */
//public class SocketWindowWordCount {
//
//    public static void main(String[] args) throws Exception {
////        int port;
////        try {
////            ParameterTool parameterTool = ParameterTool.fromArgs(args);
////            port = parameterTool.getInt("port");
////        } catch (Exception e) {
////            logger.info("no port set,use default port 9000");
////            port = 9000;
////        }
//        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
////        String hostName = "192.168.1.120";
////        String delimiter = "\n";
//        DataStreamSource<String> source = env.socketTextStream("192.168.1.120", 9000, "\n");
//        DataStream<WordCount> stream = source.flatMap(new FlatMapFunction<String, WordCount>() {
//            @Override
//            public void flatMap(String s, Collector<WordCount> out) throws Exception {
//                String[] splits = s.split("\\s");
//                for (String word : splits) {
//                    out.collect(new WordCount(word, 1L));
//                }
//            }
//        }).keyBy("word")
//                .window(SlidingProcessingTimeWindows.of(Time.seconds(2), Time.seconds(1)))
//                .reduce(new ReduceFunction<WordCount>() {
//                    @Override
//                    public WordCount reduce(WordCount a, WordCount b) {
//                        return new WordCount(a.word, a.count + b.count);
//                    }
//                });
//        stream.print().setParallelism(1);
//        env.execute();
//
//    }
//
//    public static class WordCount {
//        public String word;
//        public Long count;
//
//        public WordCount() {
//        }
//
//        public WordCount(String word, Long count) {
//            this.word = word;
//            this.count = count;
//        }
//
//        @Override
//        public String toString() {
//            return "WordCount{" +
//                    "word='" + word + '\'' +
//                    ", count=" + count +
//                    '}';
//        }
//    }
//}

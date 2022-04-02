//package com.liyu.breeze.engine.sample;
//
//import org.apache.flink.api.common.functions.FlatMapFunction;
//import org.apache.flink.api.java.DataSet;
//import org.apache.flink.api.java.ExecutionEnvironment;
//import org.apache.flink.api.java.operators.DataSource;
//import org.apache.flink.api.java.tuple.Tuple2;
//import org.apache.flink.core.fs.FileSystem;
//import org.apache.flink.util.Collector;
//
//import java.util.ArrayList;
//
///**
// * @author gleiyu
// */
//public class BatchWordCount {
//    public static void main(String[] args) throws Exception {
//        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
//        DataSource<String> text = env.fromCollection(new ArrayList<String>() {{
//            add("There are moments in life when you miss someone so much that you just want to pick them from your dreams and hug them for real! Dream what you want to dream;go where you want to go;be what you want to be,because you have only one life and one chance to do all the things you want to do");
//            add("May you have enough happiness to make you sweet,enough trials to make you strong,enough sorrow to keep you human,enough hope to make you happy? Always put yourself in others’shoes.If you feel that it hurts you,it probably hurts the other person, too");
//        }});
//        DataSet<Tuple2<String, Integer>> counts = text.flatMap(new FlatMapFunction<String, Tuple2<String, Integer>>() {
//            @Override
//            public void flatMap(String value, Collector<Tuple2<String, Integer>> out) throws Exception {
//                String[] splits = value.split("\\s");
//                for (String word : splits) {
//                    out.collect(new Tuple2<>(word, 1));
//                }
//            }
//        }).groupBy(0).sum(1);
//
////        counts.writeAsText("e:/tmp/word_count", FileSystem.WriteMode.OVERWRITE);
////        env.execute("batch word count");
//        counts.print();
//    }
//}
//

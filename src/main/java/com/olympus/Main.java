package com.olympus;

import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Main {


    public static void main(String[] args) {
        functionalChainingBasic();
        functionalChainingBasicElegant();
        functionalChainingMultipleParams();
        mapAndFilterFIreuse();
    }



    private static void functionalChainingBasic() {
        Function<String,String> fn1 = parameter -> parameter+"-1";
        Function<String,String> fn2 = parameter -> parameter+"-2";
        Function<String,String> fn3 = parameter -> parameter+"-3";

        fn1.andThen(fn2)
            .andThen(fn3)
            .apply("olympus");
    }


    private static void functionalChainingBasicElegant() {
        //Identity only accepts a chained anonymous function, not a function delegate
        Function.identity()
                .andThen(parameter -> parameter+"-1")
                .andThen(parameter -> parameter+"-2")
                .andThen(parameter -> parameter+"-3")
                .andThen(log)
                .apply("olympus");
    }


    private static void functionalChainingMultipleParams() {
        Function<String,String> fn1 = parameter -> parameter+"-1";
        Function<String, Tuple2<String,Integer>> fn2 = parameter -> Tuples.of(parameter+"-2",1);
        Function<Tuple2<String,Integer>,String> fn3 = parameter -> parameter.getT1()+"-3";
        Function<String,String> fn4 = parameter -> parameter+"-4";

        fn1.andThen(fn2)
            .andThen(fn3)
            .andThen(fn4)
            .apply("olympus");
    }

    private static void mapAndFilterFIreuse() {
        Function<String,String> fn1 = parameter -> parameter+"-1";
        Predicate<String> predicate = p -> !p.isEmpty();

        long count = Stream.of("foo", "bar", "baz")
                .map(fn1)
                .filter(predicate)
                .count();
    }


    static Function<Object,Object> log = (obj) -> {
        System.out.println(obj);
        return obj;
    };
}

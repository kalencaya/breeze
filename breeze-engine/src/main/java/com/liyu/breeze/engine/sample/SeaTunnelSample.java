package com.liyu.breeze.engine.sample;


import io.github.interestinglab.waterdrop.Waterdrop;
import io.github.interestinglab.waterdrop.config.CommandLineArgs;
import io.github.interestinglab.waterdrop.config.CommandLineUtils;
import scopt.OptionParser;

import static io.github.interestinglab.waterdrop.utils.Engine.FLINK;

public class SeaTunnelSample {
    public static void main(String[] args) {
        OptionParser<CommandLineArgs> flinkParser = CommandLineUtils.flinkParser();
        Waterdrop.run(flinkParser, FLINK, args);
    }
}

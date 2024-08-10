package com.api.sample.model;

import com.api.sample.common.util.RandomUtils;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class Lottery {
    private final Set<Integer> prize;
    private long prizeMoney = 0;
    private static final int PRICE = 1000;

    public Lottery() {
        this.prize = RandomUtils.generateLotteryNumber();
    }
    public int draw(Set<Integer> lotteryNumbers){
        Set<Integer> temp = new HashSet<>(prize);
        temp.removeAll(lotteryNumbers);
        return temp.size();
    }
    public Set<Integer> buy(){
        prizeMoney += PRICE;
        return RandomUtils.generateLotteryNumber();
    }

    public long getPrizeMoney() {
        long prizeMoney = this.prizeMoney;
        this.prizeMoney = 0;
        return prizeMoney;
    }
}

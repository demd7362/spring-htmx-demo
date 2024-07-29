package com.api.sample.controller.html;

import com.api.sample.common.annotation.HtmxRenderer;
import com.api.sample.common.exception.HtmxException;
import com.api.sample.common.model.Lottery;
import com.api.sample.common.tool.Html;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@HtmxRenderer
@RequiredArgsConstructor
public class LotteryRenderer {
    private final Lottery lottery;

    @GetMapping("/generate-lottery/textarea")
    public ResponseEntity<String> generateLottery(@RequestParam Map<String, String> params) {
        long count = Integer.parseInt(params.getOrDefault("count", "1"));
        if (count >= 10000) {
            throw new HtmxException(Html.javaScript("openDialog('에러', '한번에 10000개 이상 구매할 수 없습니다.')"));
        }
        Html root = Html.createRoot("textarea");
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < count; i++) {
            Set<Integer> lotteryNumbers = lottery.buy();
            String numbers = String.join(",", lotteryNumbers.stream().map(String::valueOf).collect(Collectors.toSet()));
            int restCount = lottery.draw(lotteryNumbers);
            if (restCount == 0) {
                Html js = Html.javaScript("openDialog('당첨', 'You got %d won!')".formatted(lottery.getPrizeMoney()));
                return ResponseEntity.ok(js.toString());
            } else {
                builder.append(numbers).append(" You got %d right".formatted(6 - restCount));
            }
            builder.append("\n");
        }
        root.addAttributes("text", builder.toString());
        return ResponseEntity.ok(root.toString());
    }
}

package org.zaber.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zaber.proxy.HitokotoResponse;
import org.zaber.proxy.OneTalkProxy;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : otter
 */
@RequiredArgsConstructor
@RestController()
@Slf4j
public class BaseController {

    @PostConstruct
    public void run() {
        initFlowRules();
    }

    @Resource(name = "ipBlockBloomFilter")
    private RBloomFilter<String> bloomFilter;

    @Autowired
    private OneTalkProxy oneTalkProxy;

    @GetMapping(path = "/bloom/add")
    public boolean add(String ip) {
        return bloomFilter.add(ip);
    }

    @GetMapping(path = "/bloom/contain")
    public boolean contain(String ip) {
        return bloomFilter.contains(ip);
    }

    @GetMapping(path = "/talk")
    public HitokotoResponse talk() {
        HitokotoResponse response = null;
        try {
            response = oneTalkProxy.callHitokotoAPI();
        } catch (Exception e) {
            log.warn("warn", e);
        }
        return response;
    }

    @GetMapping(path = "/fail")
    public boolean fail() {
        try (Entry entry = SphU.entry("fail")) {
            // 被保护的逻辑
            return false;
        } catch (BlockException ex) {
            // 处理被流控的逻辑
            return defaultResp();
        }
    }

    private boolean defaultResp() {
        return true;
    }

    void initFlowRules() {
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        rule.setResource("fail");
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // Set limit QPS to 20.
        rule.setCount(1);
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }
}

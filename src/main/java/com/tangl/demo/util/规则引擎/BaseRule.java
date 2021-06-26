package com.tangl.demo.util.规则引擎;

import com.tangl.demo.util.规则引擎.dto.RuleItemDTO;

public interface BaseRule {

    boolean execute(RuleItemDTO rule);
}

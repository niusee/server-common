/*
 * Niusee server-common
 *
 * Copyright 2015-2017 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.test.utils;

import cn.niusee.common.utils.FormUtils;
import junit.framework.TestCase;

import java.util.Map;

/**
 * 测试Form工具类
 *
 * @author Qianliang Zhang
 */
public class FormUtilsTest extends TestCase {

    public void testForm() {
        String test = "1=1&a=a&b=b&c=c";
        Map<String, String> forms = FormUtils.parseForm(test);
        assertEquals("1", forms.get("1"));
        assertNull(forms.get("2"));
        assertEquals("a", forms.get("a"));
        assertEquals("b", forms.get("b"));
        assertEquals("c", forms.get("c"));
    }
}

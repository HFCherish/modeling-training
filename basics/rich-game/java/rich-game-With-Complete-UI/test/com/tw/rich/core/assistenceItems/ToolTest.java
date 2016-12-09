package com.tw.rich.core.assistenceItems;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by pzzheng on 12/6/16.
 */
public class ToolTest {
    @Test
    public void should_able_to_find_tool_by_id() {
        assertThat(Tool.findToolById(1), is(Tool.BLOCK));
    }
}
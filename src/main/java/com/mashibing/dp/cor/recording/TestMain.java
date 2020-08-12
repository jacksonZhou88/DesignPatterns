package com.mashibing.dp.cor.recording;

import java.util.ArrayList;
import java.util.List;

/**
*<p>功能描述：责任链模式< /p>
*<ul>
*<li>@param </li>
*<li>@return </li>
*<li>@throws </li>
*<li>@author My</li>
*<li>@date 2020/4/18 9:16</li>
*</ul>
*/
public class TestMain {
    public static void main(String[] args) {
        Msg mm = new Msg();
        mm.setMsg("新人你好！欢迎来到java的世界:),详情访问www.baidu.com。");

        /*过滤阶段*/
        FilterChain chain = new FilterChain();
        UppperFilter uppperFilter = new UppperFilter();
        chain.add(uppperFilter);
        EmojjFilter emojjFilter = new EmojjFilter();
        chain.add(emojjFilter);

        FilterChain chain2 = new FilterChain();
        URLFilter urlFilter = new URLFilter();
        chain2.add(urlFilter);

        chain.add(chain2);
        chain.doFilter(mm);
        System.out.println(mm.toString());

    }


    static class Msg{
        private String msg;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        @Override
        public String toString() {
            return "msg{" +
                    "msg='" + msg + '\'' +
                    '}';
        }
    }

    public static abstract class Filter{

        abstract boolean doFilter(Msg msg);
    }

    static class UppperFilter extends Filter{

        @Override
        boolean doFilter(Msg mm){
            String str = mm.msg.replace("java", "JAVA");
            mm.setMsg(str);
            return true;
        }
    }

    static class EmojjFilter extends Filter{

        @Override
        boolean doFilter(Msg mm){
            String str = mm.msg.replace(":)", "^.^");
            mm.setMsg(str);
            return false;
        }
    }

    static class URLFilter extends Filter{

        @Override
        boolean doFilter(Msg mm){
            mm.setMsg(mm.getMsg().replace("www.baidu.com", "https://www.baidu.com"));
            return true;
        }
    }

    static class FilterChain extends Filter {
        List<Filter> filters = new ArrayList();

        public void add(Filter filter){
            filters.add(filter);
        }

        @Override
        boolean doFilter(Msg mm){
            for (Filter filter : filters) {
                if(!filter.doFilter(mm)) return false;
            }
            return true;
        }
    }

}

package com.boxfish.ming.rxjavaretrofitdemo.entity;

// +----------------------------------------------------------------------
// | CreateTime: 16/3/24 
// +----------------------------------------------------------------------
// | Author:     xab(http://www.zhangkun.net.cn)
// +----------------------------------------------------------------------
// | CopyRight:  http://www.boxfish.cn
// +----------------------------------------------------------------------

/**
 * 相同格式的Http请求数据该如何封装
 * HttpResult就相当于一个包装类，将结果包装了起来，但是在使用的时候要给出一个明确的类型。
 * @param <T>
 */
public class HttpResult<T> {

    private int count;
    private int start;
    private int total;
    private String title;

    //用来模仿Data
    private T subjects;


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public T getSubjects() {
        return subjects;
    }

    public void setSubjects(T subjects) {
        this.subjects = subjects;
    }


    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("title=" + title + " count=" + count + " start=" + start);
        if (null != subjects) {
            sb.append(" subjects:" + subjects.toString());
        }
        return sb.toString();
    }
}

package com.yuan.middleware.util;

/**
 * 雪花算法
 *
 * @author yuanjm
 * @date 2020/7/13 6:03 下午
 */
public class SnowflakeIdWorker {

    /**
     * 开始时间截
     */
    private final long twepoch = 1530051700000L;

    /**
     * 机器id的位数
     */
    private final long workerIdBits = 5L;

    /**
     * 数据标识id的位数
     */
    private final long datacenterIdBits = 5L;

    /**
     * 最大的机器id，结果是31
     */
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);

    /**
     * 最大的数据标识id，结果是31
     */
    private final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);

    /**
     * 序列的位数
     */
    private final long sequenceBits = 12L;

    /**
     * 机器ID向左移12位
     */
    private final long workerIdShift = sequenceBits;

    /**
     * 数据标识id向左移17位
     */
    private final long datacenterIdShift = sequenceBits + workerIdBits;

    /**
     * 时间截向左移22位
     */
    private final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;

    /**
     * 生成序列的掩码
     */
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    /**
     * 工作机器ID(0~31)
     */
    private long workerId;

    /**
     * 数据中心ID(0~31)
     */
    private long datacenterId;

    /**
     * 毫秒内序列(0~4095)
     */
    private long sequence = 0L;

    /**
     * 上次生成ID的时间截
     */
    private long lastTimestamp = -1L;

    /**
     * 构造函数
     *
     * @param workerId     工作ID (0~31)
     * @param datacenterId 数据中心ID (0~31)
     */
    public SnowflakeIdWorker(long workerId, long datacenterId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    /**
     * 获得下一个ID (该方法是线程安全的)
     *
     * @return SnowflakeId
     */
    public synchronized long nextId() {
        long timestamp = getCurrentTime();

        //如果当前时间小于上一次生成的时间戳，说明系统时钟回退过就抛出异常
        if (timestamp < lastTimestamp) {
            throw new RuntimeException("回拨的时间为：" + (lastTimestamp - timestamp));
        }

        //如果是同一时间生成的，则进行毫秒内序列
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            //毫秒内序列溢出
            if (sequence == 0) {
                //获得新的时间戳
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {  //时间戳改变，毫秒内序列重置
            sequence = 0L;
        }

        //上次生成ID的时间截
        lastTimestamp = timestamp;

        //移位并通过或运算拼到一起组成64位的ID
        return ((timestamp - twepoch) << timestampLeftShift) // 计算时间戳
                | (datacenterId << datacenterIdShift) // 计算数据中心
                | (workerId << workerIdShift) // 计算机器ID
                | sequence; // 序列号
    }

    /**
     * 获得新的时间戳
     *
     * @param lastTimestamp 上次生成ID的时间截
     * @return 当前时间戳
     */
    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = getCurrentTime();
        // 若是当前时间等于上一次的1时间就一直阻塞，知道获取到最新的时间（回拨后的时间）
        while (timestamp <= lastTimestamp) {
            timestamp = getCurrentTime();
        }
        return timestamp;
    }

    /**
     * 获取当前时间
     *
     * @return 当前时间(毫秒)
     */
    protected long getCurrentTime() {
        return System.currentTimeMillis();
    }
}
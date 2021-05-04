package com.tangl.demo.controller.redis;

import com.tangl.demo.annotation.LogAnno;
import com.tangl.demo.redis.RedisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * @author: TangLiang
 * @date: 2020/8/1 12:02
 * @since: 1.0
 */
@Api(tags = "Redis测试")
@Controller
@RequestMapping("/redis")
public class RedisController {
    @Autowired
    private RedisService redisService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private CacheManager redisCacheManager;

    @ApiOperation("获取缓存")
    @RequestMapping(value = "/getCache", method = RequestMethod.GET)
    @ResponseBody
    @LogAnno(operateType = "获取缓存")
    public Map getCache() {
        Map result = new HashMap();
        result.put("success", true);
        result.put("result", redisCacheManager.getCacheNames());
        return result;
    }

    @ApiOperation("获取某个缓存")
    @RequestMapping(value = "/getCacheByName", method = RequestMethod.GET)
    @ResponseBody
    //@LogAnno(operateType = "获取某个缓存")
    public RedisCache getCacheByName(String name) {
//        Map result = new HashMap();
//        result.put("success", true);
//        result.put("result", redisCacheManager.getCache(name));
        return (RedisCache) redisCacheManager.getCache(name);
    }

    @ApiOperation("获取模糊缓存")
    @RequestMapping(value = "/gets", method = RequestMethod.GET)
    @ResponseBody
    @LogAnno(operateType = "获取模糊缓存")
    public Map gets(String name) {
        Map result = new HashMap();
        result.put("success", true);
        result.put("result", redisService.gets(name));
        return result;
    }

    @ApiOperation("测试简单缓存")
    @RequestMapping(value = "/simpleTest", method = RequestMethod.GET)
    @ResponseBody
    @LogAnno(operateType = "测试简单缓存")
    public Map simpleTest() {
        Map result = new HashMap();
        String key = "redis:simple:" + "1001";
        redisService.set(key, 1000);
        result.put("success", true);
        result.put("result", redisService.get(key));
        return result;
    }

    @ApiOperation("获取简单缓存")
    @RequestMapping(value = "/getSimpleTest", method = RequestMethod.GET)
    @ResponseBody
    @LogAnno(operateType = "获取简单缓存")
    public Map getSimpleTest(String name) {
        Map result = new HashMap();
        result.put("success", true);
        result.put("result", redisService.get(name));
        return result;
    }

    @ApiOperation("测试简单缓存递增")
    @RequestMapping(value = "/simpleTestIns", method = RequestMethod.GET)
    @ResponseBody
    @LogAnno(operateType = "测试简单缓存递增")
    public Map simpleTestIns() {
        Map result = new HashMap();
        String key = "redis:simple:" + "1001";
        redisService.incr(key, 1);
        result.put("success", true);
        result.put("result", redisService.get(key));
        return result;
    }

    @ApiOperation("测试Hash结构的缓存")
    @RequestMapping(value = "/hashTest", method = RequestMethod.GET)
    @ResponseBody
    @LogAnno(operateType = "测试Hash结构的缓存")
    public Map hashTest() {
        Map result = new HashMap();
        String key = "redis:hash:" + "1002";
        Map<String, Object> value = new HashMap<>();
        value.put("id", "1");
        value.put("name", "唐三");
        redisService.hSetAll(key, value);
        result.put("success", true);
        result.put("result", redisService.hGetAll(key));
        return result;
    }

    @ApiOperation("测试Hash结构的缓存&添加一个key")
    @RequestMapping(value = "/hashTestKey", method = RequestMethod.GET)
    @ResponseBody
    @LogAnno(operateType = "测试Hash结构的缓存&添加一个key")
    public Map hashTestKey() {
        Map result = new HashMap();
        String key = "redis:hash:" + "1002";
        redisService.hSet(key, "age", 18);
        result.put("success", true);
        result.put("result", redisService.hGetAll(key));
        return result;
    }

    @ApiOperation("测试Set结构的缓存")
    @RequestMapping(value = "/setTest", method = RequestMethod.GET)
    @ResponseBody
    @LogAnno(operateType = "测试Set结构的缓存")
    public Map setTest() {
        Map result = new HashMap();
        String key = "redis:set:all";
        Map<String, Object> value = new HashMap<>();
        value.put("id", "1");
        value.put("name", "唐三");
        redisService.sAdd(key, value);
        result.put("success", true);
        result.put("result", redisService.sMembers(key));
        return result;
    }

    @ApiOperation("测试List结构的缓存")
    @RequestMapping(value = "/listTest", method = RequestMethod.GET)
    @ResponseBody
    @LogAnno(operateType = "测试List结构的缓存")
    public Map listTest() {
        String key = "redis:list:all";
        Map result = new HashMap();
        Map<String, Object> value = new HashMap<>();
        value.put("id", "1");
        value.put("name", "唐三");
        redisService.lPushAll(key, value);
        //redisService.lRemove(key, 1, value);
        result.put("success", true);
        result.put("result", redisService.lRange(key, 0, 3));
        return result;
    }

    @ApiOperation("测试List结构的缓存&根据索引获取")
    @RequestMapping(value = "/listTestIndex", method = RequestMethod.GET)
    @ResponseBody
    @LogAnno(operateType = "测试List结构的缓存&根据索引获取")
    public Map listTestIndex() {
        String key = "redis:list:all";
        Map result = new HashMap();
        Map<String, Object> value = new HashMap<>();
        value.put("id", "3");
        value.put("name", "唐三");
        redisService.lPush(key, value);
        result.put("success", true);
        result.put("size", redisService.lSize(key));
        result.put("res", redisService.lIndex(key, 3));
        result.put("result", redisService.lRange(key, 0, 3));
        return result;
    }

    /**
     * redis> INFO
     * # Server
     * # Redis服务器版本
     * redis_version:999.999.999
     * redis_git_sha1:3c968ff0
     * redis_git_dirty:0
     * redis_build_id:51089de051945df4
     * redis_mode:standalone
     * # Redis 服务器的宿主操作系统
     * os:Linux 4.8.0-1-amd64 x86_64
     * # 架构（32 或 64 位）
     * arch_bits:64
     * # Redis 所使用的事件处理机制
     * multiplexing_api:epoll
     * atomicvar_api:atomic-builtin
     * # 编译 Redis 时所使用的 GCC 版本
     * gcc_version:6.3.0
     * # 服务器进程的 PID
     * process_id:9941
     * # Redis 服务器的随机标识符（用于 Sentinel 和集群）
     * run_id:b770a8af038963f3d1b55358c2e376d0b5e00182
     * # TCP/IP 监听端口
     * tcp_port:6379
     * # 自 Redis 服务器启动以来，经过的秒数
     * uptime_in_seconds:1028993
     * # 自 Redis 服务器启动以来，经过的天数
     * uptime_in_days:11
     * hz:10
     * # 以分钟为单位进行自增的时钟，用于 LRU 管理
     * lru_clock:10750613
     * executable:/usr/local/bin/redis-server
     * config_file:
     * <p>
     * # Clients
     * # 已连接客户端的数量（不包括通过从属服务器连接的客户端）
     * connected_clients:4
     * # 当前连接的客户端当中，最长的输出列表
     * client_longest_output_list:0
     * # 当前连接的客户端当中，最大输入缓存
     * client_biggest_input_buf:0
     * # 正在等待阻塞命令（BLPOP、BRPOP、BRPOPLPUSH）的客户端的数量
     * blocked_clients:0
     * <p>
     * # Memory
     * # 由 Redis 分配器分配的内存总量，以字节（byte）为单位
     * used_memory:154272800
     * # 以人类可读的格式返回 Redis 分配的内存总量
     * used_memory_human:147.13M
     * # 从操作系统的角度，返回 Redis 已分配的内存总量（俗称常驻集大小）。这个值和 top 、 ps等命令的输出一致。
     * used_memory_rss:160612352
     * # 以人类可读的格式返回
     * used_memory_rss_human:153.17M
     * # Redis 的内存消耗峰值（以字节为单位）
     * used_memory_peak:154319968
     * # 以人类可读的格式返回 Redis 的内存消耗峰值
     * used_memory_peak_human:147.17M
     * # 使用内存达到峰值内存的百分比，即(used_memory/ used_memory_peak) *100%
     * used_memory_peak_perc:99.97%
     * # Redis为了维护数据集的内部机制所需的内存开销，包括所有客户端输出缓冲区、查询缓冲区、AOF重写缓冲区和主从复制的backlog
     * used_memory_overhead:44082040
     * # Redis服务器启动时消耗的内存
     * used_memory_startup:510704
     * # 数据占用的内存大小，即used_memory-used_memory_overhead
     * used_memory_dataset:110190760
     * # 数据占用的内存大小的百分比，100%*(used_memory_dataset/(used_memory-used_memory_startup))
     * used_memory_dataset_perc:71.66%
     * allocator_allocated:154256264
     * allocator_active:154550272
     * allocator_resident:159731712
     * # 整个系统内存
     * total_system_memory:1044770816
     * # 以更直观的格式显示整个系统内存
     * total_system_memory_human:996.37M
     * # Lua脚本存储占用的内存
     * used_memory_lua:37888
     * # 以更直观的格式显示Lua脚本存储占用的内存
     * used_memory_lua_human:37.00K
     * # Redis实例的最大内存配置
     * maxmemory:0
     * # 以更直观的格式显示Redis实例的最大内存配置
     * maxmemory_human:0B
     * # 当达到maxmemory时的淘汰策略
     * maxmemory_policy:noeviction
     * allocator_frag_ratio:1.00
     * allocator_frag_bytes:294008
     * allocator_rss_ratio:1.03
     * allocator_rss_bytes:5181440
     * rss_overhead_ratio:1.01
     * rss_overhead_bytes:880640
     * # 碎片率，used_memory_rss/ used_memory
     * mem_fragmentation_ratio:1.04
     * mem_fragmentation_bytes:6422528
     * # 在编译时指定的， Redis 所使用的内存分配器。可以是 libc 、 jemalloc 或者 tcmalloc 。
     * mem_allocator:jemalloc-4.0.3
     * active_defrag_running:0
     * lazyfree_pending_objects:0
     * <p>
     * # Persistence，RDB 持久化和 AOF 持久化有关信息
     * # 一个标志值，记录了服务器是否正在载入持久化文件
     * loading:0
     * # 距离最近一次成功创建持久化文件之后，经过了多少秒
     * rdb_changes_since_last_save:3813014
     * # 一个标志值，记录了服务器是否正在创建 RDB 文件
     * rdb_bgsave_in_progress:0
     * # 最近一次成功创建 RDB 文件的 UNIX 时间戳
     * rdb_last_save_time:1570002708
     * # 一个标志值，记录了最近一次创建 RDB 文件的结果是成功还是失败
     * rdb_last_bgsave_status:ok
     * # 记录了最近一次创建 RDB 文件耗费的秒数
     * rdb_last_bgsave_time_sec:-1
     * # 如果服务器正在创建 RDB 文件，那么这个域记录的就是当前的创建操作已经耗费的秒数
     * rdb_current_bgsave_time_sec:-1
     * rdb_last_cow_size:0
     * # 一个标志值，记录了 AOF 是否处于打开状态
     * aof_enabled:0
     * # 一个标志值，记录了服务器是否正在创建 AOF 文件
     * aof_rewrite_in_progress:0
     * # 一个标志值，记录了在 RDB 文件创建完毕之后，是否需要执行预约的 AOF 重写操作
     * aof_rewrite_scheduled:0
     * # 最近一次创建 AOF 文件耗费的时长
     * aof_last_rewrite_time_sec:-1
     * # 如果服务器正在创建 AOF 文件，那么这个域记录的就是当前的创建操作已经耗费的秒数
     * aof_current_rewrite_time_sec:-1
     * # 一个标志值，记录了最近一次创建 AOF 文件的结果是成功还是失败
     * aof_last_bgrewrite_status:ok
     * aof_last_write_status:ok
     * aof_last_cow_size:0
     * <p>
     * # Stats，一般统计信息
     * # 服务器已接受的连接请求数量
     * total_connections_received:96
     * # 服务器已执行的命令数量
     * total_commands_processed:8700000
     * # 服务器每秒钟执行的命令数量
     * instantaneous_ops_per_sec:11
     * total_net_input_bytes:710523330
     * total_net_output_bytes:109073206
     * instantaneous_input_kbps:1.04
     * instantaneous_output_kbps:0.32
     * rejected_connections:0
     * sync_full:0
     * sync_partial_ok:0
     * sync_partial_err:0
     * # 因为过期而被自动删除的数据库键数量
     * expired_keys:13569
     * expired_stale_perc:0.00
     * expired_time_cap_reached_count:0
     * #  因为最大内存容量限制而被驱逐（evict）的键数量
     * evicted_keys:0
     * # 查找数据库键成功的次数
     * keyspace_hits:1911035
     * # 查找数据库键失败的次数
     * keyspace_misses:749427
     * # 目前被订阅的频道数量
     * pubsub_channels:0
     * # 目前被订阅的模式数量
     * pubsub_patterns:0
     * # 最近一次 fork() 操作耗费的毫秒数
     * latest_fork_usec:0
     * migrate_cached_sockets:0
     * slave_expires_tracked_keys:0
     * active_defrag_hits:0
     * active_defrag_misses:0
     * active_defrag_key_hits:0
     * active_defrag_key_misses:0
     * <p>
     * # Replication，主/从复制信息
     * # 服务器角色
     * role:master
     * # 已连接的从服务器数量
     * connected_slaves:0
     * master_replid:33f9e49948d61df7fa02e315ecff02bbc3b2c9aa
     * master_replid2:0000000000000000000000000000000000000000
     * master_repl_offset:0
     * second_repl_offset:-1
     * repl_backlog_active:0
     * repl_backlog_size:1048576
     * repl_backlog_first_byte_offset:0
     * repl_backlog_histlen:0
     * <p>
     * # CPU
     * used_cpu_sys:925.48
     * used_cpu_user:3467.41
     * used_cpu_sys_children:0.00
     * used_cpu_user_children:0.00
     * <p>
     * # Cluster
     * cluster_enabled:0
     * <p>
     * # Keyspace
     * db0:keys=876234,expires=3,avg_ttl=204565882
     */
    @ApiOperation("redisTemplate获取获取redis信息&根据索引获取")
    @RequestMapping(value = "/getInfo", method = RequestMethod.GET)
    @ResponseBody
    @LogAnno(operateType = "redisTemplate获取获取redis信息&根据索引获取")
    public Map getInfo() {
        Map result = new HashMap();
        // 方式1：获取Redis缓存全部信息
        Properties info = redisTemplate.getRequiredConnectionFactory().getConnection().info();
        result.put("info", info);
        return result;
    }

    /**
     * server：有关Redis服务器的常规信息
     * clients：客户端连接部分
     * memory：内存消耗相关信息
     * persistence：RDB和AOF相关信息
     * stats：一般统计
     * replication：主/副本复制信息
     * cpu：CPU消耗统计信息
     * commandstats：Redis命令统计
     * cluster：Redis群集部分
     * keyspace：与数据库相关的统计
     * 它还可以采用以下值：
     * all：返回所有部分
     * default
     */
    @ApiOperation("获取指定Redis缓存全部信息")
    @RequestMapping(value = "/getSpeInfo", method = RequestMethod.GET)
    @ResponseBody
    @LogAnno(operateType = "获取指定Redis缓存全部信息")
    public Map getSpeInfo(String message) {
        Map result = new HashMap();
        // 方式2：根据Connection获取Redis缓存指定信息：
        Properties info = redisTemplate.getRequiredConnectionFactory().getConnection().info(message);
        result.put("info", info);
        return result;
    }

    //https://blog.csdn.net/weixin_43591980/article/details/116148153
    @ApiOperation("获取Redis缓存信息")
    @RequestMapping(value = "/getRedisInfo", method = RequestMethod.GET)
    @ResponseBody
    @LogAnno(operateType = "获取Redis缓存信息")
    public Map getRedisInfo(String message) {
        // 获取redis缓存完整信息
        //Properties info = redisTemplate.getRequiredConnectionFactory().getConnection().info();
        Properties info = (Properties) redisTemplate.execute((RedisCallback<Object>) connection -> connection.info());

        // 获取redis缓存命令统计信息
        //Properties commandStats = redisTemplate.getRequiredConnectionFactory().getConnection().info("commandstats");
        Properties commandStats = (Properties) redisTemplate.execute((RedisCallback<Object>) connection -> connection.info("commandstats"));

        // 获取redis缓存中可用键Key的总数
        //Long dbSize = redisTemplate.getRequiredConnectionFactory().getConnection().dbSize();
        Object dbSize = redisTemplate.execute((RedisCallback<Object>) connection -> connection.dbSize());

        Map<String, Object> result = new HashMap<>(3);
        result.put("info", info);
        result.put("dbSize", dbSize);

        List<Map<String, String>> pieList = new ArrayList<>();
        commandStats.stringPropertyNames().forEach(key -> {
            Map<String, String> data = new HashMap<>(2);
            String property = commandStats.getProperty(key);
            data.put("name", StringUtils.removeStart(key, "cmdstat_"));
            data.put("value", StringUtils.substringBetween(property, "calls=", ",usec"));
            pieList.add(data);
        });
        result.put("commandStats", pieList);
        return result;
    }
}

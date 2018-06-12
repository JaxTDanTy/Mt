package cn.crap.service.tool;

import cn.crap.service.ICacheService;
import cn.crap.beans.Config;
import cn.crap.utils.MyString;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * stringCache 缓存设置为10分钟
 */
@Service("stringCache")
public class StringCache implements ICacheService<String> {
	private static Cache<String, String> cache;
	public static final String CACHE_PREFIX = "str:";

	@Autowired
	private Config config;

	public Cache<String, String> getCache(){
	    if (cache == null) {
            cache = CacheBuilder.newBuilder()
                    .initialCapacity(10)
                    .concurrencyLevel(5)
                    .expireAfterWrite(10 * 60, TimeUnit.SECONDS)
                    .build();
        }
        return cache;
	}

	public boolean add(String key, String value){
		getCache().put(assembleKey(key), value);
		return true;
	}
	
	@Override
	public String get(String key){
		if(MyString.isEmpty(key)){
			return new String();
		}
		return getCache().getIfPresent(assembleKey(key));
	}

    @Override
    public boolean del(String key){
		getCache().invalidate(assembleKey(key));
        return true;
    }


	@Override
    public boolean flushDB(){
		getCache().invalidateAll();
	    return true;
    }

	private String assembleKey(String key) {
		return CACHE_PREFIX + key;
	}
}

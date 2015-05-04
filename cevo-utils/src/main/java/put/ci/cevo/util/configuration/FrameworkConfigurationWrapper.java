package put.ci.cevo.util.configuration;

import static put.ci.cevo.util.sequence.Sequences.seq;
import static put.ci.cevo.util.sequence.Sequences.transform;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Properties;

import org.apache.commons.configuration.AbstractConfiguration;
import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.event.ConfigurationEvent;
import org.apache.commons.configuration.event.ConfigurationListener;
import org.apache.commons.configuration.event.EventSource;

import put.ci.cevo.util.sequence.Sequence;
import put.ci.cevo.util.sequence.transforms.Transform;
import put.ci.cevo.util.sequence.transforms.Transforms;

public class FrameworkConfigurationWrapper extends AbstractFrameworkConfiguration {

	private AbstractConfiguration baseConfiguration;

	private LinkedHashSet<String> keys;

	private final ConfigurationListener listener = new ConfigurationListener() {
		@Override
		public void configurationChanged(ConfigurationEvent event) {
			if (!event.isBeforeUpdate()) {
				Object source = event.getSource();
				if (source instanceof AbstractConfiguration) {
					loadKeys((AbstractConfiguration) source);
				}
			}
		}
	};

	public FrameworkConfigurationWrapper(AbstractConfiguration baseConfiguration) {
		setBaseConfiguration(baseConfiguration);
	}

	public void override(AbstractConfiguration override) {
		CompositeConfiguration overridden = new ExtendedCompositeConfiguration();
		overridden.addConfiguration(override);
		overridden.addConfiguration(baseConfiguration);
		setBaseConfiguration(overridden);
	}

	private void setBaseConfiguration(AbstractConfiguration baseConfig) {
		baseConfig.addConfigurationListener(listener);
		if (baseConfiguration != null) {
			baseConfiguration.removeConfigurationListener(listener);
		}
		baseConfiguration = baseConfig;
		loadKeys(baseConfig);
	}

	private void loadKeys(AbstractConfiguration config) {
		Iterator<String> baseKeys = config.getKeys();
		keys = new LinkedHashSet<>(seq(baseKeys).toList());
	}

	public AbstractConfiguration getBaseConfiguration() {
		return baseConfiguration;
	}

	@Override
	public EventSource getEventSource() {
		return baseConfiguration;
	}

	@Override
	public boolean containsKey(ConfigurationKey key) {
		return keys.contains(key.toString());
	}

	@Override
	public String getString(ConfigurationKey key) {
		return baseConfiguration.getString(key.toString());
	}

	@Override
	public String getString(ConfigurationKey key, String defaultValue) {
		checkAndLogDefaultValueWarning(key, defaultValue);
		return baseConfiguration.getString(key.toString(), defaultValue);
	}

	private <T> void checkAndLogDefaultValueWarning(ConfigurationKey key, T defaultValue) {
		if (!baseConfiguration.containsKey(key.toString())) {
			logDefaultValueWarning(key, defaultValue);
		}
	}

	@Override
	public List<String> getList(ConfigurationKey key) {
		return seq(baseConfiguration.getList(key.toString(), Collections.emptyList())).transform(
			new Transform<Object, String>() {
				@Override
				public String transform(Object object) {
					return object.toString();
				}
			}).toList();
	}

	@Override
	public List<String> getList(ConfigurationKey key, List<String> defaultValue) {
		List<Object> list = baseConfiguration.getList(key.toString(), null);
		if (list != null) {
			return seq(list).transform(new Transform<Object, String>() {
				@Override
				public String transform(Object object) {
					return object.toString();
				}
			}).toList();
		}
		logDefaultValueWarning(key, defaultValue);
		return defaultValue;
	}

	@Override
	public Properties getProperties(ConfigurationKey key) throws IllegalArgumentException {
		return baseConfiguration.getProperties(key.toString());
	}

	@Override
	public Object getProperty(ConfigurationKey key) {
		return baseConfiguration.getProperty(key.toString());
	}

	@Override
	public boolean getBoolean(ConfigurationKey key) {
		return baseConfiguration.getBoolean(key.toString());
	}

	@Override
	public Boolean getBoolean(ConfigurationKey key, Boolean defaultValue) {
		checkAndLogDefaultValueWarning(key, defaultValue);
		return baseConfiguration.getBoolean(key.toString(), defaultValue);
	}

	@Override
	public byte getByte(ConfigurationKey key) {
		return baseConfiguration.getByte(key.toString());
	}

	@Override
	public Byte getByte(ConfigurationKey key, Byte defaultValue) {
		checkAndLogDefaultValueWarning(key, defaultValue);
		return baseConfiguration.getByte(key.toString(), defaultValue);
	}

	@Override
	public double getDouble(ConfigurationKey key) {
		return baseConfiguration.getDouble(key.toString());
	}

	@Override
	public Double getDouble(ConfigurationKey key, Double defaultValue) {
		checkAndLogDefaultValueWarning(key, defaultValue);
		return baseConfiguration.getDouble(key.toString(), defaultValue);
	}

	@Override
	public float getFloat(ConfigurationKey key) {
		return baseConfiguration.getFloat(key.toString());
	}

	@Override
	public Float getFloat(ConfigurationKey key, Float defaultValue) {
		checkAndLogDefaultValueWarning(key, defaultValue);
		return baseConfiguration.getFloat(key.toString(), defaultValue);
	}

	@Override
	public int getInt(ConfigurationKey key) {
		return baseConfiguration.getInt(key.toString());
	}

	@Override
	public Integer getInt(ConfigurationKey key, Integer defaultValue) {
		checkAndLogDefaultValueWarning(key, defaultValue);
		return baseConfiguration.getInteger(key.toString(), defaultValue);
	}

	@Override
	public long getLong(ConfigurationKey key) {
		return baseConfiguration.getLong(key.toString());
	}

	@Override
	public Long getLong(ConfigurationKey key, Long defaultValue) {
		checkAndLogDefaultValueWarning(key, defaultValue);
		return baseConfiguration.getLong(key.toString(), defaultValue);
	}

	@Override
	public short getShort(ConfigurationKey key) {
		return baseConfiguration.getShort(key.toString());
	}

	@Override
	public Short getShort(ConfigurationKey key, Short defaultValue) {
		checkAndLogDefaultValueWarning(key, defaultValue);
		return baseConfiguration.getShort(key.toString(), defaultValue);
	}

	@Override
	public BigDecimal getBigDecimal(ConfigurationKey key) {
		return baseConfiguration.getBigDecimal(key.toString());
	}

	@Override
	public BigDecimal getBigDecimal(ConfigurationKey key, BigDecimal defaultValue) {
		checkAndLogDefaultValueWarning(key, defaultValue);
		return baseConfiguration.getBigDecimal(key.toString(), defaultValue);
	}

	@Override
	public BigInteger getBigInteger(ConfigurationKey key) {
		return baseConfiguration.getBigInteger(key.toString());
	}

	@Override
	public BigInteger getBigInteger(ConfigurationKey key, BigInteger defaultValue) {
		checkAndLogDefaultValueWarning(key, defaultValue);
		return baseConfiguration.getBigInteger(key.toString(), defaultValue);
	}

	@Override
	public Sequence<ConfigurationKey> getKeys(ConfigurationKey prefix) {
		if (prefix == null) {
			return transform(keys, Transforms.<String, ConfigurationKey> construct(ConfKey.class, String.class));
		}
		return transform(baseConfiguration.getKeys(prefix.toString()),
			Transforms.<String, ConfigurationKey> construct(ConfKey.class, String.class));
	}

}

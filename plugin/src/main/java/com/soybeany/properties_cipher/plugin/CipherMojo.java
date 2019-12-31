package com.soybeany.properties_cipher.plugin;

import com.soybeany.properties_cipher.util.CipherPropertyUtils;
import com.soybeany.properties_cipher.util.CipherUtils;
import com.soybeany.properties_cipher.util.UserInfo;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.util.List;
import java.util.Properties;

@Mojo(name = "cipher")
public class CipherMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project}")
    private MavenProject project;

    @Parameter
    private UserInfo userInfo;

    public void execute() {
        // 读取pom中的properties
        Properties properties = project.getProperties();
        if (null == userInfo) {
            userInfo = new UserInfo();
        }
        // 加密属性
        CipherPropertyUtils.encryptProperties(userInfo, properties);
        // 将加密使用的信息写入配置文件(使用默认key)
        Properties cipherInfoProperties = new Properties();
        cipherInfoProperties.setProperty(CipherUtils.CIPHER_INFO_KEY, UserInfo.toString(userInfo));
        try {
            CipherPropertyUtils.write(CipherUtils.DEFAULT_INFO, cipherInfoProperties, getCipherInfoFile());
        } catch (Exception e) {
            throw new RuntimeException("无法写入加密信息文件", e);
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private File getCipherInfoFile() {
        try {
            List<String> elements = project.getRuntimeClasspathElements();
            if (elements.isEmpty()) {
                throw new Exception("运行时类路径为空");
            }
            File dir = new File(elements.get(0));
            dir.mkdirs();
            return new File(dir, CipherPropertyUtils.CIPHER_INFO_PROPERTIES);
        } catch (Exception e) {
            throw new RuntimeException("配置文件获取失败", e);
        }
    }
}

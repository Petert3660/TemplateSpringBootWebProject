package com.ptconsultancy;

import com.ptconsultancy.admin.BuildVersion;
import com.ptconsultancy.entities.UpdateEntity;
import com.ptconsultancy.repositories.UpdateEntityRepository;
import com.ptconsultancy.users.Role;
import com.ptconsultancy.users.User;
import com.ptconsultancy.users.UserRepository;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.thymeleaf.util.StringUtils;

@SpringBootApplication
@PropertySource("classpath:application.properties")
public class Application implements CommandLineRunner {

    @Autowired
    private Environment env;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UpdateEntityRepository updateEntityRepository;

    public static void main(String[] args) throws Throwable {
        new SpringApplicationBuilder(Application.class).run(args);
    }

    @Override
    public void run(String... strings) throws Exception {
        populateDatabase();
        if (strings == null) {
            prepareRunnableBatchFile();
        }
        outputMessage();
    }

    private void outputMessage() {
        String serverPort = env.getProperty("server.port");
        System.out.println("Simple Message Board Running, Version: " + BuildVersion.getBuildVersion());
        System.out.println("************************************************************************");
        System.out.println("* The application is now running on:- localhost:" + serverPort + "                   *");
        System.out.println("************************************************************************");
    }

    private void prepareRunnableBatchFile() throws IOException {

        String projectFilename = "";
        File file = new File("build\\libs");
        projectFilename = findJar(projectFilename, file);

        file = new File("run.bat");
        if (file.exists()) {
            file.delete();
        }

        if (!StringUtils.isEmpty(projectFilename) && projectFilename.contains(".jar")) {
            RandomAccessFile fout = new RandomAccessFile("run.bat", "rw");

            fout.writeBytes("cd build\\libs\n\n");
            fout.writeBytes("java -jar " + projectFilename + " no-file");

            fout.close();
        } else {
            file = new File(".");
            projectFilename = findJar(projectFilename, file);

            file = new File("run.bat");
            if (file.exists()) {
                file.delete();
            }

            if (!StringUtils.isEmpty(projectFilename) && projectFilename.contains(".jar")) {
                RandomAccessFile fout = new RandomAccessFile("run.bat", "rw");

                fout.writeBytes("cd build\\libs\n\n");
                fout.writeBytes("java -jar " + projectFilename + " no-file");

                fout.close();
            } else {
                System.out.println("************************************************************************");
                System.out.println("* There does not seem to be any projectFilename set.                   *");
                System.out.println("* Try running the gradlew clean build first, and then create run.bat!  *");
                System.out.println("************************************************************************");
            }
        }
    }

    private String findJar(String projectFilename, File file) {
        if (file.exists()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].getName().contains(".jar")) {
                    projectFilename = files[i].getName();
                }
            }
        }
        return projectFilename;
    }

    private void populateDatabase() {

        if (userRepository.findByUserName("superuser").size() == 0) { // Using H2 in-memory DB
            // Create some users
            String prop;
            int i = 1;
            do {
                String user = "user" + String.valueOf(i++);
                prop = env.getProperty(user);
                if (!StringUtils.isEmpty(prop)) {
                    String[] userDetails = prop.split(", ");
                    if (userDetails[2].equals("admin")) {
                        userRepository.save(new User(userDetails[0], userDetails[1], Role.ADMIN, userDetails[3], userDetails[4], true));
                    } else if (userDetails[2].equals("user")) {
                        userRepository.save(new User(userDetails[0], userDetails[1], Role.USER, userDetails[3], userDetails[4], false));
                    }
                }
            } while (!StringUtils.isEmpty(prop));

            //Create and publish an initial post
            UpdateEntity firstPost = new UpdateEntity("#admin", "Welcome", "superuser", "Welcome to this message board that allows users to update details of their most recent activity!", LocalDateTime.now());
            updateEntityRepository.save(firstPost);
        }
    }
}

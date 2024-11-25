package ru.romansib.otus;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import ru.romansib.otus.service.MenuService;

@ComponentScan
public class Application {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        ApplicationContext context = new AnnotationConfigApplicationContext(Application.class);
        MenuService menuService = (MenuService) context.getBean("menuServiceImpl");
        System.out.println("First session");
        menuService.startSession(context);
        System.out.println("Second session (new cart bean)");
        menuService.startSession(context);
    }
}
package ru.otus.booklibrarymongodb.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class AppProp {

    @Value("${spring.data.mongodb.database}")
    private String mongoDBName;
}

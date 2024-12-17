package com.example.store.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Aspect
@Component
public class CodeGenerationAspect {

    @PersistenceContext
    private EntityManager entityManager;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    // Bắt cả save và saveAll (các phương thức bắt đầu bằng save)
    @Before("execution(* org.springframework.data.repository.CrudRepository.save*(..)) && args(entities)")
    public void generateCode(Object entities) throws Throwable {
        if (entities instanceof Iterable) {
            // Nếu entities là một Iterable (List, Set...), lặp qua từng entity và sinh mã
            List<?> entityList = (List<?>) entities;
            if(entityList.size() <= 0) return;
            // Lấy số mã code lớn nhất hiện có trong cơ sở dữ liệu
            Long maxCode = getMaxCode(entityList.get(0).getClass());

            // Lặp qua từng entity và tạo mã code mới
            for (int i = 0; i < entityList.size(); i++) {
                Object entity = entityList.get(i);
                Long nextCodeNumber = maxCode + i; // Tăng mã code cho mỗi entity trong list
                generateCodeForEntity(entity, nextCodeNumber);
            }
        } else {
            // Nếu là đối tượng đơn, sinh mã cho nó
            Long maxCode = getMaxCode(entities.getClass());
            generateCodeForEntity(entities, maxCode);
        }
    }

    private void generateCodeForEntity(Object entity, Long nextCodeNumber) throws Throwable {
        // Kiểm tra nếu entity có annotation @AutoGenerateCode
        AutoGenerateCode annotation = entity.getClass().getAnnotation(AutoGenerateCode.class);
        if (annotation != null) {
            String prefix = annotation.prefix();
            String datePart = LocalDateTime.now().format(FORMATTER);

            // Tạo mã code mới, ví dụ: PRD-20231201-001
            String newCode = prefix + "-" + datePart + "-" + String.format("%05d", nextCodeNumber);

            // Đặt mã code cho entity
            entity.getClass().getMethod("setCode", String.class).invoke(entity, newCode);
        }
    }

    private Long getMaxCode(Class<?> entityClass) {
        String tableName = entityManager.getMetamodel().entity(entityClass).getName();
        Long maxCode = entityManager.createQuery(
                        "SELECT COUNT(e) FROM " + tableName + " e", Long.class)
                .getSingleResult();
        return maxCode + 1;
    }
}
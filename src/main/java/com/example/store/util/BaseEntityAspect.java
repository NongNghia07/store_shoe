//package com.example.store.util;
//
//
//import com.example.store.entity.BaseEntity;
//import com.example.store.entity.Users;
//import com.example.store.service.JwtService;
//import com.example.store.service.UserService;
//import jakarta.servlet.http.HttpServletRequest;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import java.util.List;
//import java.util.UUID;
//
//@Aspect
//@Component
//
//public class BaseEntityAspect {
//    @Autowired
//    private JwtService jwtService;
//    @Autowired
//    private UserService userService;
//
//    // Định nghĩa một pointcut cho các phương thức save và saveAll trong repository
//    @Pointcut("execution(* com.example.store.repository..save*(..))")
//    public void saveMethods() {}
//
//    // Trước khi gọi các phương thức save hoặc saveAll
//    @Before("saveMethods() && args(entity)")
//    public void setCreateOrUpdateUser(JoinPoint joinPoint, Object entity) {
//        // Bỏ qua xử lý nếu entity là Role (nếu bạn không muốn áp dụng cho Role)
////        if (entity instanceof Role) {
////            return;
////        }
//
//        // Lấy HttpServletRequest từ RequestContextHolder
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//
//        if (attributes != null) {
//            HttpServletRequest request = attributes.getRequest();
//
//            if (request != null) {
//                // Lấy token từ header Authorization
//                String token = request.getHeader("Authorization");
//                if (token != null && token.startsWith("Bearer ")) {
//                    token = token.substring(7); // Bỏ "Bearer " khỏi token
//                    // Extract user ID từ token
//                    String userName = jwtService.extractUsername(token);
//                    Users user = userService.findUserByUserName(userName);
//                    // Kiểm tra nếu entity là BaseEntity và gán createBy/updateBy
//                    if (entity instanceof BaseEntity) {
//                        BaseEntity baseEntity = (BaseEntity) entity;
//                        setCreateOrUpdateFields(baseEntity, user.getId());
//                    }
//
//                    // Nếu entity là danh sách các đối tượng
//                    if (entity instanceof List<?>) {
//                        List<?> entities = (List<?>) entity;
//                        for (Object item : entities) {
//                            if (item instanceof BaseEntity) {
//                                BaseEntity baseEntity = (BaseEntity) item;
//                                setCreateOrUpdateFields(baseEntity, user.getId());
//                            }
//                        }
//                    }
//                }
//            }
//        } else {
//            // Nếu không có request, xử lý mặc định hoặc bỏ qua
//            UUID defaultUserId = UUID.fromString("00000000-0000-0000-0000-000000000000"); // Ví dụ giá trị mặc định
//            if (entity instanceof BaseEntity) {
//                BaseEntity baseEntity = (BaseEntity) entity;
//                setCreateOrUpdateFields(baseEntity, defaultUserId);
//            }
//        }
//    }
//
//    // Phương thức gán giá trị createBy và updateBy cho entity
//    private void setCreateOrUpdateFields(BaseEntity baseEntity, UUID userId) {
//        if (baseEntity.getCreateBy() == null) {
//            baseEntity.setCreateBy(userId);  // Gán createBy nếu nó null
//        }
//        baseEntity.setUpdateBy(userId);  // Luôn cập nhật updateBy
//    }
//}

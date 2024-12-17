package com.example.store;

import com.example.store.dto.request.RoleRequestDTO;
import com.example.store.dto.request.UsersRequestDTO;
import com.example.store.entity.LanguagePriority;
import com.example.store.entity.MetadataTranslations;
import com.example.store.entity.Role;
import com.example.store.repository.LanguagePriorityRepository;
import com.example.store.repository.MtTranslationsRepository;
import com.example.store.repository.RoleRepository;
import com.example.store.repository.UserRepository;
import com.example.store.service.AuthenticationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.Instant;
import java.util.*;

@SpringBootApplication
@ServletComponentScan
@EnableJpaAuditing(auditorAwareRef = "auditorAware") // Bật tính năng JPA Auditing
public class StoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(StoreApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(AuthenticationService service,
											   RoleRepository roleRepository,
											   UserRepository userRepository,
											   LanguagePriorityRepository languagePriorityRepository,
											   MtTranslationsRepository mtTranslationsRepository) {
		return args -> {
			// Tạo ngôn ngữ ưu tiên nếu chưa có trong cơ sở dữ liệu
			if(languagePriorityRepository.count() == 0) {
				List<LanguagePriority> languagePriorities = new ArrayList<>();
				languagePriorities.add(new LanguagePriority(UUID.randomUUID(), "1", "en", Instant.now()));
				languagePriorities.add(new LanguagePriority(UUID.randomUUID(), "2", "vi", Instant.now()));
				languagePriorityRepository.saveAll(languagePriorities);
			}

			// Tạo các vai trò nếu chưa có trong cơ sở dữ liệu
			if (roleRepository.count() == 0) {
				Role adminRole = new Role();
				adminRole.setName("ADMIN");
				adminRole.setIsStatus(true);
				adminRole = roleRepository.save(adminRole);
				MetadataTranslations translationsAdmin = new MetadataTranslations();
				translationsAdmin.setTableName("role");
				translationsAdmin.setColumnName("description");
				translationsAdmin.setLanguageCode("vi");
				translationsAdmin.setTranslation("ADMIN quyền cao nhất. Quản lý toàn bộ");
				translationsAdmin.setRowId(adminRole.getId());
				mtTranslationsRepository.save(translationsAdmin);

				Role managerRole = new Role();
				managerRole.setName("MANAGER");
				managerRole.setIsStatus(true);
				managerRole = roleRepository.save(managerRole);
				MetadataTranslations translationsManager = new MetadataTranslations();
				translationsManager.setTableName("role");
				translationsManager.setColumnName("description");
				translationsManager.setLanguageCode("vi");
				translationsManager.setTranslation("Quản lý: Quản lý các chức năng được ADMIN cung cấp");
				translationsManager.setRowId(managerRole.getId());
				mtTranslationsRepository.save(translationsManager);

				Role staffRole = new Role();
				staffRole.setName("STAFF");
				staffRole.setIsStatus(true);
				staffRole = roleRepository.save(staffRole);
				MetadataTranslations translationsStaff = new MetadataTranslations();
				translationsStaff.setTableName("role");
				translationsStaff.setColumnName("description");
				translationsStaff.setLanguageCode("vi");
				translationsStaff.setTranslation("Nhân viên: Quản lý các chức năng được phân");
				translationsStaff.setRowId(staffRole.getId());
				mtTranslationsRepository.save(translationsStaff);

				Role customerRole = new Role();
				customerRole.setName("CUSTOMER");
				customerRole.setIsStatus(true);
				customerRole = roleRepository.save(customerRole);
				MetadataTranslations translationsCustomer = new MetadataTranslations();
				translationsCustomer.setTableName("role");
				translationsCustomer.setColumnName("description");
				translationsCustomer.setLanguageCode("vi");
				translationsCustomer.setTranslation("khách hàng.");
				translationsCustomer.setRowId(customerRole.getId());
				mtTranslationsRepository.save(translationsCustomer);
			}


			// Tạo tài khoản admin nếu chưa có
			if (userRepository.count() == 0) {
				Role role = roleRepository.findByName("ADMIN");
				Set<RoleRequestDTO> roles = new HashSet<>();
				RoleRequestDTO roleDTO = new RoleRequestDTO();
				roleDTO.setId(role.getId());
				roles.add(roleDTO);
				UsersRequestDTO adminRequest = UsersRequestDTO.builder()
						.userName("admin")
						.password("123")
						.name("Nông Quang Nghĩa")
						.phone("0964545439")
						.email("nghia@gmail.com")
						.isStatus(true)
						.roles(roles)
						.build();
				service.register(adminRequest);
			}
		};
	}
}

package com.bilgeadam.service;

import com.bilgeadam.dto.request.BranchRequestDto;
import com.bilgeadam.dto.response.BranchResponseDto;
import com.bilgeadam.dto.response.EmployeeResponseDto;
import com.bilgeadam.mapper.UserServiceMapper;
import com.bilgeadam.repository.IBranchRepository;
import com.bilgeadam.repository.entity.Branch;
import com.bilgeadam.repository.entity.Course;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BranchService {
    private final IBranchRepository branchRepository;
    private final UserServiceMapper mapper;


    public BranchService(IBranchRepository branchRepository, UserServiceMapper mapper) {
        this.branchRepository = branchRepository;
        this.mapper = mapper;
    }

    public void save(BranchRequestDto dto) {
        Branch branch = mapper.mapDtoToBranch(dto);
        branchRepository.save(branch);
    }

    public List<BranchResponseDto> listAllCourse() {
        List<Branch> branchList = branchRepository.findAll();
        List<BranchResponseDto> branchResponseDtoList = new ArrayList<>();
        for (Branch branch : branchList
        ) {
            EmployeeResponseDto employeeResponseDto = mapper.mapEmployeetoDto(branch.getManager());
            BranchResponseDto branchResponseDto = BranchResponseDto.builder().id(branch.getId()).name(branch.getName())
                    .address(branch.getAddress()).district(branch.getDistrict()).province(branch.getProvince())
                    .manager(employeeResponseDto).build();
            branchResponseDtoList.add(branchResponseDto);
        }
        return branchResponseDtoList;
    }

    public BranchResponseDto getBranchDetailById(long id) {
        Optional<Branch> branchDb = branchRepository.findById(id);
        if (branchDb.isPresent()) {
            Branch branch = branchDb.get();
            for (Course course:branch.getCourses()
                 ) {
                log.info(course.getName());
            }
            BranchResponseDto dto = BranchResponseDto.builder().id(branch.getId()).name(branch.getName()).address(branch.getAddress())
                    .province(branch.getProvince()).district(branch.getDistrict())
                    .manager(mapper.mapEmployeetoDto(branch.getManager())).build();
            return dto;
        }
        return new BranchResponseDto();
    }
    public boolean updateBranch(BranchResponseDto dto){
        Branch branch=branchRepository.findById(dto.getId()).get();
       branch.setName(dto.getName());
       branch.setAddress(dto.getAddress());
       branch.setManager(mapper.mapDtoToEmployee(dto.getManager()));
       branch.setDistrict(dto.getDistrict());
       branch.setProvince(dto.getProvince());
       branchRepository.save(branch);
       return true;
    }

}

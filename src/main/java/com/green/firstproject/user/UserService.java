package com.green.firstproject.user;

import com.green.firstproject.user.model.*;
import com.green.firstproject.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper mapper;

    @Value("${file.dir}")
    private String fileDir;


    public int insUser(UserInsDto dto) {
        UserEntity entity = new UserEntity();
        entity.setName(dto.getName());


        int result = mapper.insUser(entity);
        if (result == 1) {
            return entity.getIuser();
        }
        return result;

    }



    public int upUserPic(MultipartFile pic, UserPicDto dto) {
        String centerPath = String.format("user/%d", dto.getIuser());
        String dicPath = String.format("%s/%s", fileDir, centerPath);


        File dic = new File(dicPath); // 폴더 생성 여부 확인 및 생성, 삭제 수행
        if (!dic.exists()){//폴더가 존재하지 않는 경우
            dic.mkdirs(); //생성 수행
        }
        String originFileName = pic.getOriginalFilename();
        String savedFileName = FileUtils.makeRandomFileNm(originFileName);
        String savedFilePath = String.format("%s/%s", centerPath, savedFileName);
        String targetPath    = String.format("%s/%s", fileDir, savedFileName);
        File target = new File(targetPath);
        try {
            pic.transferTo(target);

        }catch (Exception e){
            return 0;
        }
        dto.setMainPic((savedFilePath));
        try {
            int result = mapper.upUserPic(dto);
            if (result == 0){
                throw new Exception("프로필 사진을 등록할 수 없습니다.");
            }
        }catch (Exception e){
            //파일 삭제
            target.delete();
            return 0;
        }
        return 1;
    }


    public int upAllUser(UserAllDto dto) {
        return mapper.upAllUser(dto);
    }

    public List<UserListVo> selUser(UserListOneDto dto) {
        dto.setIuser(dto.getIuser());
        return mapper.selUser(dto);
    }

    public List<UserAllListVo> selAllUser() {
        return mapper.selAllUser();
    }

}

package com.gensoft.saasapi.service;

import com.gensoft.core.web.ApiResult;
import com.gensoft.core.web.BusinessException;
import com.gensoft.dao.user.User;
import com.gensoft.dao.user.UserRepository;
import com.gensoft.dao.verification.VerificationCode;
import com.gensoft.dao.verification.VerificationCodeRepository;
import com.gensoft.saasapi.cache.UserInfoCache;
import com.gensoft.saasapi.pojo.user.RegisterReq;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.gensoft.saasapi.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by alan on 16-5-24.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    VerificationService verificationService;

    @Autowired
    FileUtil fileUtil;

    @Autowired
    UserInfoCache userInfoCache;

    @Transactional
    public ApiResult register(RegisterReq req, MultipartFile logoFile) throws BusinessException {
        Long mobile = req.getMobile();
        if (existsUserMobile(mobile))
            return ApiResult.failedInstance("register", ApiResult.CODE_MOBILE_ALREADY_EXISTS);
        String username = req.getUsername();
        if (null != getUserByName(username))
            return ApiResult.failedInstance("register", ApiResult.CODE_USERNAME_ALREADY_EXISTS);
        String verificationCode = req.getVerificationCode();
        if (!"noCode".equals(verificationCode) && verificationService.invalidVerificationCode(mobile, verificationCode, 1))
            return ApiResult.failedInstance("register", ApiResult.CODE_INVALIDE_VERIFICATION_CODE);
        User user = new User();
        user.setUsername(username);
        user.setNickname(req.getNickname());
        user.setPassword(req.getPassword());
        user.setMobile(mobile);
        if (logoFile != null) {
            String logoPath = fileUtil.saveFileToPath(logoFile);
            user.setLogo(logoPath);
        }
        user.setPlateNo(req.getPlateNo());
        user.setUpdateDate(new Date());
        user.setCreateDate(new Date());
        userRepository.save(user);
        userInfoCache.refreshUserMap();
        return ApiResult.successInstance("register", user);
    }

    public User getUserByName(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean existsUserMobile(Long mobile) {
        List<User> user = userRepository.getUserByMobile(mobile);
        return (user.size() > 0);
    }

    public List<User> getUserfindAll() {
        List<User> userList = userRepository.findAllUsers();
        return userList;
    }

    public List<User> getUserFindLikeName(String likeName) {
        List<User> userList = (List<User>) userRepository.getUserFindLikeName("%" + likeName + "%");
        return userList;
    }

    public User getUserById(Long id) {
        return userRepository.findOne(id);
    }

    public void update(User user) {
        userRepository.save(user);
    }

}

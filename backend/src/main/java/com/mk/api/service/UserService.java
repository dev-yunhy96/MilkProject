package com.mk.api.service;

import com.mk.api.dto.request.*;
import com.mk.config.JwtTokenProvider;
import com.mk.db.entity.User;
import com.mk.db.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


import java.util.Optional;

@Slf4j
@Service
public class UserService {
	
	private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
	private JwtTokenProvider jwtTokenProvider;
	private JwtTokenService jwtTokenService;
	private ModelMapper modelMapper;
	
	@Autowired
	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider, JwtTokenService jwtTokenService) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtTokenProvider = jwtTokenProvider;
		this.jwtTokenService = jwtTokenService;
		this.modelMapper = new ModelMapper();
	}

	@Transactional
	public boolean createUser(UserDTO userDto)  {
		//throws AlreadyExistEmailException, AlreadyExistNicknameException
		String email = userDto.getEmail();
		Optional<User> userByEmail = userRepository.findByEmail(email);
		String nickname = userDto.getNickname();
		Optional<User> userByNickname = userRepository.findByNickname(nickname);

		if (userByEmail.isPresent()) {
			log.info("user email already exists");
			//throw new AlreadyExistEmailException();
		}

		if (userByNickname.isPresent()) {
			log.info("user nickname already exists");
			//throw new AlreadyExistNicknameException();
		}

		User entity = modelMapper.map(userDto, User.class);
		entity.setPassword(passwordEncoder.encode(entity.getPassword()));
		userRepository.save(entity);
		return true;
	}


	//Login ???????????? ??????, JWT??? ???????????? ?????????
	@Transactional
	public String login(LoginReq data) {
		User user = userRepository.findByEmail(data.getEmail()).orElseThrow(()->new UsernameNotFoundException("???????????? ?????? ??? ????????????.") );
		if(comparePassword(data.getPassword(), user.getPassword())) {
			return jwtTokenProvider.createToken(user,user.getRoles());
		}
		
		return "";
	}

	//???????????? ????????? ??? ?????? ??????
	public String login2(LoginReq data) {
		User user = userRepository.findByEmail(data.getEmail()).orElseThrow(()->new UsernameNotFoundException("???????????? ?????? ??? ????????????.") );
		if(data.getPassword().equals(user.getPassword())) {
			return jwtTokenProvider.createToken(user,user.getRoles());
		}

		return "";
	}


	public UserDTO getUserById(String accessToken) {
		try {
			User user = jwtTokenService.convertTokenToUser(accessToken);
			return modelMapper.map(user, UserDTO.class);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}


	public UserDTO getUserByEmail(String email) throws Exception {
		Optional<User> source = userRepository.findByEmail(email);
		if(!source.isPresent()){
			return null;
		}
		log.info("gutUserByEmail" + source.toString());

		return modelMapper.map(source.get(), UserDTO.class);
	}

	@Transactional
	public boolean updatePassword(UpdatePasswordReq updatePasswordReq, String email) {
		Optional<User> user = userRepository.findByEmail(email);
		if(user.isPresent()){
			user.get().setPassword(passwordEncoder.encode(updatePasswordReq.getNewPassword()));
			userRepository.save(user.get());
			return true;
		}
		return false;

	}

	// ?????? ??????
	public boolean createWallet(WalletReq walletReq) {
		String email = walletReq.getOwnerId();
		String walletAddress = walletReq.getAddress();
		Optional<User> user = userRepository.findByEmail(email);
		if(user.isPresent()) {
			user.get().setWalletAddress(walletAddress);
			user.get().setWalletPrivateKey(passwordEncoder.encode(walletReq.getPrivateKey()));
			userRepository.save(user.get());
			return true;
		}
		return false;
	}

	// ????????? ?????? ?????? ??????
	public String getWalletByEmail(String email) {
		Optional<User> user = userRepository.findByEmail(email);
		if(user.isPresent()) {
			return user.get().getWalletAddress();
		}
		return null;
	}
	// PrivateKey(?????? ???????????? ??????)
	public boolean checkPrivateKey(WalletReq walletReq) {
		User user = userRepository.findByEmail(walletReq.getOwnerId()).get();
		if(comparePrivateKey(walletReq.getPrivateKey(), user.getWalletPrivateKey())) {
			return true;
		}
		return false;
	}

	public boolean comparePrivateKey(String rawPrivateKey, String encryptPrivateKey) {
		return passwordEncoder.matches(rawPrivateKey, encryptPrivateKey);
	}
	//??? matchPassword
	//??? ???????????????, ???????????? ??????????????? ????????????, ??? ??????????????? ?????? ????????? ??????
	private boolean comparePassword(String rawPassword, String encryptPassword) {
        return passwordEncoder.matches(rawPassword,encryptPassword);
    }

	@Transactional
    public boolean delete(String id) {
		Optional<User> user = userRepository.findById(id);
		if(user.isPresent() && user.get().isDelYn() ==false){
			user.get().setDelYn(true);
			userRepository.save(user.get());
			return true;
		}else{
			return false;
		}
    }

	public boolean updateLocation(String accessToken, LocationReq locationReq) {
		User user = jwtTokenService.convertTokenToUser(accessToken);
		if (user == null) {
			return false;
		}
		user.setBcode(locationReq.getBcode());
		user.setBname(locationReq.getBname());
		user.setSigungu(locationReq.getSigungu());
		userRepository.save(user);
		return true;
	}

	public String getNickname(String email) {
		Optional<User> user = userRepository.findByEmail(email);
		if(user.isPresent() && user.get().isDelYn() == false)
			return user.get().getNickname();
		return null;
	}


}

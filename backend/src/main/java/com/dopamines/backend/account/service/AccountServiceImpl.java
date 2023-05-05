package com.dopamines.backend.account.service;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.dopamines.backend.account.dto.SearchResponseDto;
import com.dopamines.backend.account.entity.Account;
import com.dopamines.backend.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    @Override
    public Account editNickname(String email, String nickname) {
        log.info("AccountServiceImpl의 editNickname에서 찍는 nickname: " + nickname);

        // 닉네임 중복확인
        validateDuplicateNickname(nickname);

        Optional<Account> optional = accountRepository.findByEmail(email);
        Account account = null;

        if(optional.isEmpty()) {
            account = new Account();
            log.info("AccountServiceImpl의 editNickname에서");
        }else {
            account = optional.get();
            account.setNickname(nickname);
            accountRepository.save(account);
        }

        return account;
    }

    private void validateDuplicateNickname(String nickname) {

        if (accountRepository.existsByNickname(nickname)) {
            throw new RuntimeException("이미 존재하는 nickname입니다.");
        }
    }

    @Override
    public Account editProfileMessage(String email, String profileMessage) {
        log.info("AccountServiceImpl의 에서 찍는 profileMessage: " + profileMessage);

        Optional<Account> optional = accountRepository.findByEmail(email);
        Account account = null;

        if(optional.isEmpty()) {
            account = new Account();
            log.info("AccountServiceImpl의 profileMessage에서");

        }else {
            account = optional.get();
            account.setProfileMessage(profileMessage);
            accountRepository.save(account);
        }

        return account;
    }

    @Override
    public void deleteAccount(String email) {
        log.info("AccountServiceImpl의 에서 찍는 email: " + email);

        Optional<Account> optional = accountRepository.findByEmail(email);
        Account account = null;

        if(optional.isEmpty()) {
            account = new Account();
            log.info("AccountServiceImpl의 deleteAccount에서");

        }else {
            account = optional.get();
            account.setDeleted(true);
            account.setNickname(null);
            account.setProfile(null);
            account.setRefreshToken(null);
            account.setProfileMessage(null);
            account.setKakaoId(null);
//            account.setEmail(null);

            accountRepository.save(account);
        }
    }

    @Override
    public ArrayList<SearchResponseDto> searchNickname (String keyword) {
        List<Account> accounts = accountRepository.findByNicknameContaining(keyword);

        ArrayList<SearchResponseDto> result = new ArrayList<SearchResponseDto>();

        for (Account account : accounts){
            log.info("AccountServiceImpl: " + account);
            SearchResponseDto searchResponseDto = new SearchResponseDto();

            searchResponseDto.setNickname(account.getNickname());
            searchResponseDto.setProfile(account.getProfile());
            searchResponseDto.setProfileMessage(account.getProfileMessage());
            result.add(searchResponseDto);

        }

        return result;
    }

    @Override
    public Account editProfile(String email, MultipartFile file) {
        log.info("AccountServiceImpl의 에서 찍는 file: " + file);

        Optional<Account> optional = accountRepository.findByEmail(email);
        Account account = null;

        if(optional.isEmpty()) {
            account = new Account();
            log.info("AccountServiceImpl의 profileMessage에서");

        }else {
            // 받아 온 파일을 naver cloud에 저장 후 url로 반환

            String profile = fileToUrl(file);
            account = optional.get();
            account.setProfile(profile);
            accountRepository.save(account);
        }

        return account;
    }

    private String fileToUrl(MultipartFile file) {

        // S3 client
        final AmazonS3 s3 = AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endPoint, regionName))
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
                .build();

        String bucketName = "sample-bucket";
        String objectName = "sample-large-object";

        File file = new File("/tmp/sample.file");
        long contentLength = file.length();
        long partSize = 10 * 1024 * 1024;

        try {
            // initialize and get upload ID
            InitiateMultipartUploadResult initiateMultipartUploadResult = s3.initiateMultipartUpload(new InitiateMultipartUploadRequest(bucketName, objectName));
            String uploadId = initiateMultipartUploadResult.getUploadId();

            // upload parts
            List<PartETag> partETagList = new ArrayList<PartETag>();

            long fileOffset = 0;
            for (int i = 1; fileOffset < contentLength; i++) {
                partSize = Math.min(partSize, (contentLength - fileOffset));

                UploadPartRequest uploadPartRequest = new UploadPartRequest()
                        .withBucketName(bucketName)
                        .withKey(objectName)
                        .withUploadId(uploadId)
                        .withPartNumber(i)
                        .withFile(file)
                        .withFileOffset(fileOffset)
                        .withPartSize(partSize);

                UploadPartResult uploadPartResult = s3.uploadPart(uploadPartRequest);
                partETagList.add(uploadPartResult.getPartETag());

                fileOffset += partSize;
            }

            // abort
            // s3.abortMultipartUpload(new AbortMultipartUploadRequest(bucketName, objectName, uploadId));

            // complete
            CompleteMultipartUploadResult completeMultipartUploadResult = s3.completeMultipartUpload(new CompleteMultipartUploadRequest(bucketName, objectName, uploadId, partETagList));
        } catch (AmazonS3Exception e) {
            e.printStackTrace();
        } catch(SdkClientException e) {
            e.printStackTrace();
        }

    }

//    @PutMapping(value = "/profile",
//            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Map<String, Object>> putMyProfileImg(
//            @RequestPart(value = "file", required = false) MultipartFile file,
//            @RequestPart(value = "nickname") String nickname,
//            @RequestPart(value = "phone", required = false) String phone,
//            @RequestHeader HttpHeaders requestHeader) {
//        log.info("테스트 닉네임: " + nickname);
//        Long userId = tokenUtils.getUserIdFromHeader(requestHeader);
//        userService.putMyProfileImg(userId, file, nickname, phone);
//        UserInventoryResponse result = userService.getProfile(userId);
//        Map<String, Object> resMap = statusCodeGeneratorUtils.checkResultByObject(result);
//        return ResponseEntity.ok().body(resMap);
//    }
}

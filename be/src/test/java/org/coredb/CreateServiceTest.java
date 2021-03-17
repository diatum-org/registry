package org.coredb.registry.test;

import java.util.*;

import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.security.*;
import java.security.spec.*;
import java.util.Base64;

import org.coredb.registry.api.AmigoApi;
import org.coredb.registry.model.AmigoMessage;

@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
public class CreateServiceTest {

  @Autowired
  private AmigoApi emigoApi;

  @Test
  public void A_createService() throws Exception {

    ResponseEntity<Void> set;
    AmigoMessage msg = new AmigoMessage();

    // test fail 
    msg.setKey("30820222300d06092a864886f70d01010105000382020f003082020a02820201008ed4bc609844479538067d6d468d7eb8736ab05167ffae63ad042d451457d1c693f6889dfe20b44d0a47eda6539730a1c37d38905ed586b816620f9aea75f90c98dd4c0977b3ec636cf2570ac69913a7d4be823076ba88b3ce9faabb8db11de48283c50a0e7a31be255135f6e8a8af877ddeb38763539036d439d336f370fb604735173d762fc99015bb4ba1a7385c3028844d399c70f819af87eefd2ede4cb44bc1364c810b169de5cf83514fe2e4cdded7031a69b5b7d13ed30ebdcabea5a54030fe1d01729273af812c539eb542d3c5068f15e78bf06786ffab8669d0098ee30532ad7700c365d1a24d107b550c389d14556da4112e1c2500fdc24bd01c7a952880e0991a17aa5b9c055b399410c6c031ed9fb15e1b1927babe7dcbe2d7ea175fa2a6eec148aa7f722b2dd4d924a56e650232663c8acfa21c4ead4ec24c12a181b02bbd2cdccec7646c094941aa4d81d88a4fc32185f17d32d7969b3c1605cb3e824028f7e9d7af071992e64b56bde417f6c656a919e1576abdc35171f4e4ab2f30083e390570c931af8736bc7e4e609105de8ab44276721991d24c3e22ebfdc4f9973610b960c7a376e49eb45b68d5d4cfa3fb31172b61d41cad989e7e0dd14d7e567e1c9713a8039fa64d6b1504e88fd8aff084ec381bd8f7507cf26e9e2986b3ecead6c1e8414710ea7e90b95a7c7267d792c49fe862707f7e7ea4d9d10203010001");
    msg.setKeyType("RSA");
    msg.setSignature("68f9bdf90dd700fcda6872670eaa56eab3ee3c5af0427dd6cd0cd5cddbbd57c1850f4698d370dd7a5facc7dbb9959666346ebce79dbc41849d316de45938d8380d98eb9087e8f26bef64e6d6bb1628f429685f4319df0725e037e73876d7a23a7e18347e7b0e06e1c629b2224300f0a2e6559ba370c676dc332cc02653fbd1aae7f0ebcc1e9c2ad7984bf83b61df7b8a0b8a4825a8f52be021cccfc13e4661f0f12dd3fd2550b72116410ac74615f0723a3d13fd5ef9ccd805acc6a8afe7a503ced127d20eb8d5475e86121dae28157d601dc88ac9fbccf3326d571975dfbb7935bb06fd6cc251870278ca6fc997a194fd9b19aa82a1ff5ebcbce2a0531e331759122782a9f0df81a3daf24b4130f90d16661febd785703f71c038126eba8d82f2afb11886ad9b9d8119844417b187c250147655396d1732ab73bdbe656defc27bbd53eae52bf4091eb7df9c1da4ee5fb7fd012945635d0efbbf83b9149e14c648783e07abffdcf13c3ee7f1aa8c007911f1871b3f4654fb65f03863ec79dcab95d258c36b75e1dd3a8ff1a595b843c22d443da52d6f0845c8df8cf5918caf65c35748720b4eefa5f704c8189d7a4de1a9d84314d3d3cd38068e3a53fd5fa16310c3111419a88d1879757b9433726aad18da6895d84d10b4eea314088a95df49b7f1f3b8d9716970fc5f6c8a07e6ec06ea920cf97a5a4e5c3aa7246dd2426882");
    msg.setData("eyJlbWlnb0lkIjoiMmRiM2E1MjBhMjc1ZDVhYWVjZGYxYjI1ZTkzYTYzMWJjMDRmNGNmM2Q5Y2E4N2UwZDlkNDU1MjEwNjRlN2VlNiIsIm5hbWUiOiJUZXN0IFNlcnZpY2UgTmFtZSIsImRlc2NyaXB0aW9uIjoidGVzdCBzZXJ2aWNlIGRlc2NyaXB0aW9uIiwibG9nbyI6InRlc3RpbWFnZWRhdGEiLCJub2RlIjoibG9jYWxob3N0Ojg0MzIiLCJyZWdpc3RyeSI6InRlc3RzZXJ2aWNlcmVnaXN0cnk6ODQ0MyIsInJldmlzaW9uIjo3LCJ2ZXJzaW9uIjoiMC4wLjEiLCJoYW5kbGUiOiJ0ZXN0c2VydmljZWhhbmRsZSJ9");
    set = emigoApi.setMessage(msg);
    assertEquals(HttpStatus.NOT_ACCEPTABLE, set.getStatusCode());

    // fail if signature doesnt match
    msg.setKey("30820222300d06092a864886f70d01010105000382020f003082020a028202010094ec414edd791b284bd602b0298a46a3d162625549225759114d745890341ec260692ad4c3691f2051fd1a87aab649937a7e61ef4eb6a23ad9d3a6a7e3822a6c2cb1e953614fdafa48d1e45208435e53aebc0971d46cd31cd4988ff7c3a05b5237e409dec256fb5c85be73e9a747b981cf7f4da68dc185af51d73f1bb3c18599b24c44b0283c2832c6ee082ffd7fd9f9021b232ab0c1feff6c3c0bebf00ba7f06e428baecbbf4223b50ba4a498867263ceb569e98c78e73ce55a5d0ccb9e2d4118b396b18e341bca0cbfe839a09732853a138798b60372f73ff45ae80f17ec061555314a7a5cc05b1a749ddd6875fe146ab06cff2101106b36c05f65ef667491dbc97c3dc0faf6738d6a088429e391bc9a3612263bf342d7881ef1ee45d01ba1ec2e448bf9a767ae3568791efe6311674b86ac4edd2980a01ea235bb4e9136eb78a1a5ef3398d8735d7119bffa6488546af4415e480886489bb1724d7177b21b625064db1632654bdfda15b04647c6b5d8be4b64d22fa2cdc261ad14da9e5dd6b2a9c62a66496369ae2f363d19e6e83eaec92bfd98292ce3793a44ea8711a29e7622655c793a145d0655516a34e49ffb958e3e7e3cf46f1d07020ede7a958b2e4b07f64fab51b171c687cbe4d18bba9538d040f9e90a7d07dbd7ad88ede65cdf6d8ac12fce8f4138c27055af7aab351ce39d09e918af0a9af1ec161875d2f9890203010001");
    msg.setKeyType("RSA");
    msg.setSignature("6cd2d84dd41db72fcaab26afabe2b059cfa4463c82d7261d26a85239437e61ae4b95a3d8c557e85b0a4d435f35892906f7fda2b692438bbd296b0bbe8f91f07f95e31e2b4359a3f18ff4789e8e2ca21f0c14f54e8dca0bf95afb1b46d6b2e39e3d6a07ee3ece00599182c4b803482912c3ae1c90b5b47be4de8d5c38366eb3ece9f863c9266ff53c3c86ee30dff6cd7fbb371a1e38b83da76163e8c51b951bf5d7d605b74f2bca2e4c0d73606b9791059bd080a2d6d3aa0e82e206f18f189f143b29a18f518fe22b4f3e2215f0aadc4c4157a02b5ab49f3ba6004fed25cd1f8454e3f46aa1500d64f0660781fb82ddff6ad5a6ce253e372064f05a87e381bf68a183de01ea5e74ad223dc6b723365589f10ddb41800277101d850526a078c4ee78c57d617b5f96dbd6a7f65acd0fbbdbbce47467bc891e93b07b99f4fe096fe19f08223d29ee1b399387b1f21b4a4fa2b130f920fa6a38dc79b49f4a6c86194bf2a1885b18ff4dcdbcbaec4161c8426ed8788bf1bc33d3d6c72dd492b54cf154622c303618be149776223384a11953470f98faa6f0c74cc30e971036d9be06684517e02ce2e53f2ad3e51159daca417fb5ffed04212417015df696a47e261e62deecfe716ce688c18e0d16e97e0e6251466e055f87cf6883980980b68fa0b02e00090bfdba94bda9843bd3cbd1d0e60f789d804ae1033c10218b92ff234cd62a");
    msg.setData("eyJlbWlnb0lkIjoiNTI5ZGNhMzRlNDM3NDdlNjhhYjRjNWFmM2NkZjUwOTNjN2VkZjYwZTIwNjA2YmNlZGE0YTM0NmQ0NzQ1OWYwMCIsIm5hbWUiOiJUZXN0IFNlcnZpY2UgTmFtZSIsImRlc2NyaXB0aW9uIjoidGVzdCBzZXJ2aWNlIGRlc2NyaXB0aW9uIiwibG9nbyI6InRlc3RpbWFnZWRhdGEiLCJub2RlIjoibG9jYWxob3N0Ojg0MzIiLCJyZWdpc3RyeSI6InRlc3RzZXJ2aWNlcmVnaXN0cnk6ODQ0MyIsInJldmlzaW9uIjo4LCJ2ZXJzaW9uIjoiMC4wLjEiLCJoYW5kbGUiOiJ0ZXN0c2VydmljZWhhbmRsZSJ9");
    set = emigoApi.setMessage(msg);
    assertEquals(HttpStatus.BAD_REQUEST, set.getStatusCode());
  }

}


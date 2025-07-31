# 포트홀 모니터링 서비스를 위한 백엔드 서버

## 1. 개요
차량에 탑재된 엣지 디바이스를 통해 포트홀을 탐지하고, 2차 검증 서버에서 재분석을 거친 후 최종적으로 DB에 저장됩니다.

포트홀 모니터링 서비스는 AI 기반 탐지를 통해 포트홀의 위치 정보와 보수 현황을 제공하여, 도로 유지 관리에 효율성을 높입니다.

## 2. 시스템 구성도
<table>
  <tr>
    <td align="center" width="50%">
      <img width="679" height="611" alt="image" src="https://github.com/user-attachments/assets/47e41a44-d4b6-463b-a07a-7bab7b71510c" /><br/>
      <sub>시스템 아키텍처</sub>
    </td>
  </tr>
</table>
<br/>

<table>
  <tr>
    <td align="center" width="50%">
      <img width="100%" height="592" alt="image" src="https://github.com/user-attachments/assets/b40a69ab-64e1-4d6b-9dd5-30c0af38b902" /><br/>
      <sub>기능 명세서</sub>
    </td>
    <td align="center" width="50%">
      <img width="100%" height="592" alt="image" src="https://github.com/user-attachments/assets/e3dc7834-4a30-439d-b806-e596479c754e" /><br/>
      <sub>ERD 다이어그램</sub>
    </td>
  </tr>
</table>
<br/>

## 3. 기술 스택
- SpringBoot 3.3.0
- Build Tool Gradle 0 groovy
- JAVA 17
- PostgreSQL
- AWS S3

## 4. 주요 기능
<table>
  <tr>
    <td align="center" width="50%">
      <h5>메인 페이지</h5>
      <img width="100%" height="756" alt="image" src="https://github.com/user-attachments/assets/b5a68c39-7194-4d5b-b75e-c6cda01a682a" /><br/>
    </td>
    <td align="center" width="50%">
      <h5>포트홀 통계</h5>
      <img width="1590" height="756" alt="image" src="https://github.com/user-attachments/assets/704dd664-dcde-4e6d-b298-f3eee70623e6" /><br/>
    </td>
  </tr>
  <tr>
    <td align="center" width="50%">
      <h5>포트홀 정보</h5>
      <img width="1590" height="753" alt="image" src="https://github.com/user-attachments/assets/9c7b5d01-8220-4866-8a04-62fd92dfdb49" />
    </td>
    <td align="center" width="50%">
      <h5>보수 처리 페이지</h5>
      <img width="3556" height="1825" alt="image" src="https://github.com/user-attachments/assets/c0dd25dd-c4e2-4dba-aea8-ee6535758f21" />
  </tr>
</table><br/>
사용자가 직접 포트홀을 신고하고 등록할 수 있는 신고 기능과, YOLO를 통해 자동 검출된 포트홀 및 사용자 신고 포트홀을 통합 관리 및 모니터링할 수 있는 기능을 구현했습니다.
포트홀 등록 시에는 지도 API를 활용하여 좌표 데이터를 기반으로 해당 위치의 상세 주소를 자동 조회하는 로직도 포함되어 있습니다.

이를 통해 관리자는 포트홀의 통계 정보 및 보수 현황을 실시간으로 확인하고 효율적으로 모니터링할 수 있습니다. <br/><br/>

<table>
  <tr>
    <td align="center" width="50%">
      <h5>지오해시 기능</h5>
      <img width="1176" height="877" alt="image" src="https://github.com/user-attachments/assets/75888917-0f3e-48f2-9ebe-23427223cb96" /><br/>
    </td>
    <td align="center" width="50%">
      <h5>로그인 페이지</h5>
      <img width="1602" height="757" alt="image" src="https://github.com/user-attachments/assets/6054d988-77ba-469e-8d91-8e08e3bf4c2b" /><br/>
    </td>
  </tr>
  <tr>
    <td align="center" width="50%">
      <h5>presigned url</h5>
      <img width="1099" height="628" alt="image" src="https://github.com/user-attachments/assets/9cb3c0aa-9bc5-4f79-9f1d-833e9e85a41e" /><br/>
    </td>
    <td align="center" width="50%">
      <h5>https 통신</h5>
      <img width="800" height="400" alt="image" src="https://github.com/user-attachments/assets/263f2987-4ea7-4b21-b5fc-fd1f59dc5c26" /><br/>
  </tr>
</table><br/>

로그인 기능은 JWT 기반의 Spring Security 인증 방식으로 구현했습니다. 또한, AWS Route53을 사용해 도메인을 설정하고, SSL 인증서를 발급받아 백엔드 서버가 https 통신을 지원하도록 구성했습니다. 

이 외에도, 백엔드 서버는 엣지 디바이스 및 2차 검증 서버를 위한 API도 제공합니다. 지오해시 알고리즘을 도입하여 포트홀 데이터에 지오해시 칼럼을 추가하고, 엣지 디바이스에서 동일한 포트홀이 중복 탐지되는 문제를 해결했습니다. 또한, Presigned URL 방식을 통해 S3 이미지 업로드 및 접근을 편리하게 처리할 수 있도록 구현했습니다. <br/><br/>

## 5. 역할
| <div align="center"><a href="https://github.com/nicehcy2">👑 허찬영</a></div> | <div align="center"><a href="https://github.com/Octoping925">문영채</a></div> | <div align="center"><a href="https://github.com/lym0217">이영민</a></div> | <div align="center"><a href="https://github.com/hyunnnchoi">최현목</a></div> |
| :---------------------------: | :---------------------------: | :---------------------------: | :---------------------------: |
| [<img src="https://github.com/nicehcy2.png" width="250" height="250">](https://github.com/nicehcy2) | [<img src="https://github.com/Octoping925.png" width="250" height="250">](https://github.com/Octoping925) | [<img src="https://github.com/lym0217.png" width="250" height="250">](https://github.com/lym0217) | [<img src="https://github.com/hyunnnchoi.png" width="250" height="250">](https://github.com/hyunnnchoi) |
|     `백엔드`<br/>`기계학습`<br/>`클라우드`<br/>         |         `백엔드`<br/>`프론트엔드`<br/>         |         `프론트엔드`<br/>`클라우드`<br/>         |         `기계학습`<br/>`클라우드`<br/>        |

## 6. 🔗 Quick Jump
- [[프론트엔드 Repository]](https://github.com/OpenRoot-KW/Pothole-Detection-Monitoring-Page)
- [[엣지 디바이스 Repository]](https://github.com/nicehcy2/YOLOv5-Pothole-Detection-Edge-Device)
- [[2차 AI 서버 Repository]](https://github.com/nicehcy2/YOLOv8-Pothole-Detection-Server)

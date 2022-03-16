<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!doctype html>
<html lang="en">

<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

    <title>놀멍쉬멍</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" 교차 출처>
    <link href="https: //fonts.googleapis.com/css2? 가족= 도+현 &family= Hi+Melody & display=swap" rel="stylesheet">
    <style>
        * {
            font-family: '도현', sans-serif;
            font-family: 'Hi Melody', 필기체;
        }
        #car{
        width : 1100px;
        height : 660px;
        }
    </style>
</head>

<body class="bg-gray">
	<%@ include file = "../common/menubar.jsp" %>
    <!--carousel-->
    <div class="container">
        <div id="carouselExampleCaptions" class="carousel slide" data-bs-ride="carousel">
            <div class="carousel-indicators">
                <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="0" class="active"
                    aria-current="true" aria-label="Slide 1"></button>
                <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="1"
                    aria-label="Slide 2"></button>
                <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="2"
                    aria-label="Slide 3"></button>
            </div>
            <div class="carousel-inner">
                <div class="carousel-item active text-lg">
                    <img src="resources/캐러셀1번.jpeg"
                        class="d-block w-100 car">
                    <div class="carousel-caption d-none d-md-block">
                        <h5>천지연폭포</h5>
                        <p>제주도 3대 폭포 중 하나인 천지연폭포! 조면질 안산암의 기암절벽이 하늘 높이 치솟아 <br>마치 선계로 들어온 것만 같은 황홀경을 선하는 제주도 자연 명소랍니다.
                        </p>
                    </div>
                </div>
                <div class="carousel-item">
                    <img src="resources/캐러셀2번.jpeg"
                        class="d-block w-100 car" alt="...">
                    <div class="carousel-caption d-none d-md-block">
                        <h5>신천리 벽화마을</h5>
                        <p>제주도 성산읍 신천리에 위치하고 있는 신천리벽화마을. 신천리복지회관을 시작으로 마을 담벼락과 주민이 살고 있는 집 외벽에 아름다운 벽화들이 그려져 있는 마을이랍니다.
                            '아트빌리지'로 변신한 마을 곳곳에 그려진 색색의 다양한 벽화들을 둘러보며 산책하듯 걸어볼 수 있답니다. 그린마을로 지정될 만큼 한적하고 깨끗한 마을이기 때문에
                            편안한 휴식을 원하는 분들에게 알맞은 여행지에요.</p>
                    </div>
                </div>
                <div class="carousel-item">
                    <img src="resources/캐러셀3번.jpeg"
                        class="d-block w-100 " id="car">
                    <div class="carousel-caption d-none d-md-block">
                        <h5>제주 서귀포 외돌개</h5>
                        <p>제주 서귀포 외돌개는 국가지정문화재 명승 제79호인 자연 명소랍니다. 외돌개는 화산이 폭발하여 분출된 용암지대에 파도의 침식 작용으로 형성된 돌기둥이 홀로 서 있어서
                            붙여진 이름이라고 해요.
                        </p>
                    </div>
                </div>
            </div>

            <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleCaptions"
                data-bs-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Previous</span>
            </button>
            <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleCaptions"
                data-bs-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Next</span>
            </button>
        </div>
    </div>
    <!--content-->
    <div class="container">
        <div class="row my-5">
            <div class="col-8 text-lg-start">
                <br><br><br>
                <h1><b>놀멍쉬멍이란,</b></h1>
                <h3>놀면서 쉬면서의 제주도 방언으로 여러분들의 니즈에 맞는 여행과 명소를 추천해드립니다.</h3>
            </div>
            <div class="col-4"><img src="../resources/사진1.jpg" alt="" class="w-100">
            </div>
        </div>
        <hr />

        <div class="row my-5">
            <div class="col-4 text-lg-start">
                <img src="/Users/sonms/Desktop/web/제목을 입력해주세요_-001 (1).jpg" alt="" class="w-100">
            </div>
            <div class="col-8">
                <br><br><br>
                <h1><b>다양한 테마가 있는 여행</b></h1>
                <h3>테마에 맞게 여행을 하는거에요 어때요 왜안바뀌세요</h3>
            </div>
        </div>
        <hr />
        <div class="row my-5">
            <div class="col-8 text-lg-start">
                <br><br><br>
                <h1><b>놀멍쉬멍이란,</b></h1>
                <h3>놀면서 쉬면서의 제주도 방언으로 여러분들의 니즈에 맞는 여행과 명소를 추천해드립니다.</h3>
            </div>
            <div class="col-4"><img src="/Users/sonms/Desktop/web/제목을 입력해주세요_ _ 복사본-001.png" alt="" class="w-100">
            </div>
        </div>
        <div class="col-12 text-center">
            <a class="btn btn-warning" href="views/common/login.jsp">시작하기</a>
        </div>
    </div>

    <!--footer-->
    <hr />
    <div class="text-center">
        <p>Copyright 2022. Normung.All rights reserved.</p>
    </div>
    </div>
    <!-- Optional JavaScript; choose one of the two! -->

    <!-- Option 1: Bootstrap Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
        crossorigin="anonymous"></script>

    <!-- Option 2: Separate Popper and Bootstrap JS -->
    <!--
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js" integrity="sha384-7+zCNj/IqJ95wo16oMtfsKbZ9ccEh31eOz1HGyDuCQ6wgnyJNSYdrPa03rtR1zdB" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js" integrity="sha384-QJHtvGhmr9XOIpI6YVutG+2QOK9T+ZnN4kzFN1RtK3zEFEIsxhlmWl5/YESvpZ13" crossorigin="anonymous"></script>
    -->
</body>

</html>
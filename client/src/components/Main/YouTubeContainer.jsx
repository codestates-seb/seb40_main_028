import axios from "axios";
import React, { useEffect, useState } from "react";
import ScrollContainer from "react-indiana-drag-scroll";

const YouTubeContainer = () => {
  const API = process.env.REACT_APP_YOUTUBE_API_KEY;
  const [selectedVideo, setSelectedVideo] = useState(null);
  const [videos, setVideos] = useState([
    {
      kind: "youtube#searchListResponse",
      etag: "CFG4EQy5vb18AIzxsfK9u35ohMs",
      nextPageToken: "CAUQAA",
      regionCode: "KR",
      pageInfo: {
        totalResults: 1000000,
        resultsPerPage: 5,
      },
      items: [
        {
          kind: "youtube#searchResult",
          etag: "TLKantlufAhO96Dutn4mlHTt6HY",
          id: {
            kind: "youtube#video",
            videoId: "USm6aZ0J6Js",
          },
          snippet: {
            publishedAt: "2020-08-03T07:39:49Z",
            channelId: "UCF97u_Y5C3FxAcD0nn1DCKQ",
            title: "헬스 초보자가 꼭 해야 할 운동과 하지 말아야 할 운동",
            description:
              "헬스를 시작했을 때 부상을 막고 안전하게 근육량을 늘리기 위해 필요한 필수템 정보를 남겨드립니다-! 벤치프레스 할 때 손목이 ...",
            thumbnails: {
              default: {
                url: "https://i.ytimg.com/vi/USm6aZ0J6Js/default.jpg",
                width: 120,
                height: 90,
              },
              medium: {
                url: "https://i.ytimg.com/vi/USm6aZ0J6Js/mqdefault.jpg",
                width: 320,
                height: 180,
              },
              high: {
                url: "https://i.ytimg.com/vi/USm6aZ0J6Js/hqdefault.jpg",
                width: 480,
                height: 360,
              },
            },
            channelTitle: "보통사람을 위한 운동채널",
            liveBroadcastContent: "none",
            publishTime: "2020-08-03T07:39:49Z",
          },
        },
        {
          kind: "youtube#searchResult",
          etag: "CbshxqFmQ-k3WJMi-K7YG1punLI",
          id: {
            kind: "youtube#video",
            videoId: "afccIbcbU8g",
          },
          snippet: {
            publishedAt: "2021-08-06T22:00:01Z",
            channelId: "UCXtYRuK83skNw84dYS7my7g",
            title:
              "(거북목, 굽은 어깨,굽은 등) 나쁜 자세 3종 세트 한번에 교정하기 -부르거 운동 - 송영민의 바른자세만들기 #34",
            description:
              "나쁜자세에서 바른자세로 교정하기 위한 4번째 시간 이번에는 부르거 운동!! 거북목, 굽은 어깨, 굽은 등을 한 동작으로 펼 수 있다고 ...",
            thumbnails: {
              default: {
                url: "https://i.ytimg.com/vi/afccIbcbU8g/default.jpg",
                width: 120,
                height: 90,
              },
              medium: {
                url: "https://i.ytimg.com/vi/afccIbcbU8g/mqdefault.jpg",
                width: 320,
                height: 180,
              },
              high: {
                url: "https://i.ytimg.com/vi/afccIbcbU8g/hqdefault.jpg",
                width: 480,
                height: 360,
              },
            },
            channelTitle: "굿라이프",
            liveBroadcastContent: "none",
            publishTime: "2021-08-06T22:00:01Z",
          },
        },
        {
          kind: "youtube#searchResult",
          etag: "rfshsMXbX119BFlZKESqdbxSLjM",
          id: {
            kind: "youtube#video",
            videoId: "EoGMVSORHtM",
          },
          snippet: {
            publishedAt: "2022-01-23T03:00:05Z",
            channelId: "UCdtRAcd3L_UpV4tMXCw63NQ",
            title:
              "어깨가 넓어지고 싶다? 이 운동 하나라도 제대로 배우시면 됩니다.",
            description:
              "어깨 #어깨깡패 #어깨운동 어깨가 넓어지고 싶으신가요? 그럼 이 운동을 한 번 해보시는건 어떨까요? 바로.. 밀리터리 프레스.",
            thumbnails: {
              default: {
                url: "https://i.ytimg.com/vi/EoGMVSORHtM/default.jpg",
                width: 120,
                height: 90,
              },
              medium: {
                url: "https://i.ytimg.com/vi/EoGMVSORHtM/mqdefault.jpg",
                width: 320,
                height: 180,
              },
              high: {
                url: "https://i.ytimg.com/vi/EoGMVSORHtM/hqdefault.jpg",
                width: 480,
                height: 360,
              },
            },
            channelTitle: "피지컬갤러리",
            liveBroadcastContent: "none",
            publishTime: "2022-01-23T03:00:05Z",
          },
        },
        {
          kind: "youtube#searchResult",
          etag: "YhrpdS5M0MnAOHLlAZRIuy97egU",
          id: {
            kind: "youtube#video",
            videoId: "YdhHnZxcpgY",
          },
          snippet: {
            publishedAt: "2019-07-12T07:30:01Z",
            channelId: "UC3hRpIQ4x5niJDwjajQSVPg",
            title:
              "사이드 레터럴 레이즈 자세 완벽정리 I 승모근 개입없이 어깨운동 하는법 feat.내추럴 어깨뽕만들기",
            description:
              "안녕하세요 다이어터 여러분, 핏블리 문석기입니다! 오늘은 사이드 레터럴 레이즈에 대해 설명해보았습니다:) 보통 초보자분들에겐 ...",
            thumbnails: {
              default: {
                url: "https://i.ytimg.com/vi/YdhHnZxcpgY/default.jpg",
                width: 120,
                height: 90,
              },
              medium: {
                url: "https://i.ytimg.com/vi/YdhHnZxcpgY/mqdefault.jpg",
                width: 320,
                height: 180,
              },
              high: {
                url: "https://i.ytimg.com/vi/YdhHnZxcpgY/hqdefault.jpg",
                width: 480,
                height: 360,
              },
            },
            channelTitle: "핏블리 FITVELY",
            liveBroadcastContent: "none",
            publishTime: "2019-07-12T07:30:01Z",
          },
        },
        {
          kind: "youtube#searchResult",
          etag: "Lr_iJmSq72RnsBaXmlEFLvOTsAo",
          id: {
            kind: "youtube#video",
            videoId: "kz84Fc6HGu4",
          },
          snippet: {
            publishedAt: "2019-05-03T09:00:08Z",
            channelId: "UC3hRpIQ4x5niJDwjajQSVPg",
            title:
              "아직도 스쿼트 그렇게 하세요? 스쿼트 할때 꼭 봐야하는 영상! | 스쿼트 하는법 고퀄 영상공개",
            description:
              "스쿼트 #고관절 #3대운동 안녕하세요 다이어터 여러분, 문석기입니다 오늘은 하체 운동의 꽃, 3대 운동 중 하나인 스쿼트에 대해 ...",
            thumbnails: {
              default: {
                url: "https://i.ytimg.com/vi/kz84Fc6HGu4/default.jpg",
                width: 120,
                height: 90,
              },
              medium: {
                url: "https://i.ytimg.com/vi/kz84Fc6HGu4/mqdefault.jpg",
                width: 320,
                height: 180,
              },
              high: {
                url: "https://i.ytimg.com/vi/kz84Fc6HGu4/hqdefault.jpg",
                width: 480,
                height: 360,
              },
            },
            channelTitle: "핏블리 FITVELY",
            liveBroadcastContent: "none",
            publishTime: "2019-05-03T09:00:08Z",
          },
        },
      ],
    },
  ]);

  // 유튜브 API 개인 키 할당량 초과로 인해 임시로 데이터를 넣어놓음
  // useEffect(() => {
  //   axios
  //     .get(
  //       `https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults=5&q=운동자세&key=${API}`
  //     )
  //     .then((res) => {
  //       console.log("succ");
  //       setVideos(res.data.items);
  //     })
  //     .catch((err) => {
  //       console.log(err);
  //     });
  // }, []);
  // `${https://www.youtube.com/channel/}+video.snippet.channelId`

  return (
    <div className="w-full min-h-[13.5em] py-4 overflow-x-scroll mb-[5em] pr-4">
      <ScrollContainer>
        <div className="flex w-full">
          {videos ? (
            videos[0].items.map((video, idx) => {
              const thumbnails = video.snippet.thumbnails.high.url.replace(
                "hqdefault.jpg",
                "maxresdefault.jpg"
              );
              return (
                <div
                  role="presentation"
                  onClick={() => {
                    setSelectedVideo(idx);
                  }}
                  className="image-div flex flex-col text-sm font-semibold min-w-[30em] h-full mr-4 text-center first:ml-4"
                  key={idx}
                >
                  {selectedVideo === idx ? (
                    <div className="w-full h-[20em] relative">
                      <img
                        src={thumbnails}
                        className="thumnails w-full max-h-inital  object-contain rounded-md aspect-video overflow-hidden blur-[0.15em]"
                        alt="video-thumbnails"
                      />
                      <button
                        type="button"
                        onClick={() => {
                          window.open(
                            `https://www.youtube.com/watch?v=${video.id.videoId}`
                          );
                        }}
                        className="absolute left-[42.5%] top-[37.5%] font-semibold text-white bg-d-button hover:bg-d-button-hover rounded-md p-2"
                      >
                        보러가기
                      </button>
                    </div>
                  ) : (
                    <img
                      src={thumbnails}
                      className="thumnails w-full max-h-inital  object-contain rounded-md aspect-video overflow-hidden "
                      alt="video-thumbnails"
                    />
                  )}
                </div>
              );
            })
          ) : (
            <p>Loading...</p>
          )}
        </div>
      </ScrollContainer>
    </div>
  );
};

export default YouTubeContainer;

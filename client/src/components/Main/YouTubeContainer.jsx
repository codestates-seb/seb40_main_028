import axios from "axios";
import React, { useEffect, useState } from "react";
import ScrollContainer from "react-indiana-drag-scroll";
import Loading from "../Loading";

const YouTubeContainer = () => {
  const API = process.env.REACT_APP_YOUTUBE_API_KEY;
  const [selectedVideo, setSelectedVideo] = useState(null);
  const [videos, setVideos] = useState(null);

  useEffect(() => {
    axios
      .get(
        `https://api.apify.com/v2/acts/jupri~youtube-browser/runs/last/dataset/items?token=apify_api_${API}`
      )
      .then((res) => {
        setVideos(res.data);
      })
      .catch((err) => {
        console.log(err);
      });
  }, []);

  return (
    <div className="w-full min-h-[13.5em] py-4 overflow-x-scroll mb-[5em] pr-4">
      <ScrollContainer>
        <div className="flex w-full">
          {videos ? (
            videos.map((video, idx) => {
              const videoUrl = video.url;
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
                        src={video.thumbnails[0].url}
                        className="thumnails w-full max-h-inital object-contain rounded-md aspect-video overflow-hidden blur-[0.15em]"
                        alt="video-thumbnails"
                      />
                      <button
                        type="button"
                        onClick={() => {
                          window.open(videoUrl);
                        }}
                        className="absolute left-[42.5%] top-[35.5%] font-semibold text-white bg-d-button hover:bg-d-button-hover rounded-md p-2"
                      >
                        보러가기
                      </button>
                    </div>
                  ) : (
                    <img
                      src={video.thumbnails[0].url}
                      className="thumnails w-full max-h-inital  object-contain rounded-md aspect-video overflow-hidden "
                      alt="video-thumbnails"
                    />
                  )}
                </div>
              );
            })
          ) : (
            <div className="image-div flex text-sm font-semibold min-w-[30em] h-full mr-4 text-center first:ml-4">
              {Array(10)
                .fill(0)
                .map((_, idx) => (
                  <div
                    key={idx}
                    className="thumnails min-w-[420px] min-h-[236.25px] max-h-inital object-contain rounded-md aspect-video mr-4 overflow-hidden bg-d-light"
                  >
                    <Loading />
                  </div>
                ))}
            </div>
          )}
        </div>
      </ScrollContainer>
    </div>
  );
};

export default YouTubeContainer;

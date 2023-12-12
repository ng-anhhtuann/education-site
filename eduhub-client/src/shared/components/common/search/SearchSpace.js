import React, { useState } from "react";
import "./search.css";
import CourseService from "../../../service/courseService";
import eventEmitter from "../../../utils/emitter";

const SearchSpace = () => {
  const [textInput, setTextInput] = useState("");
  const [tags, setTags] = useState([]);
  const [tagInput, setTagInput] = useState("");
  const [showTagInput, setShowTagInput] = useState(false);
  const [min, setMin] = useState(0);
  const [max, setMax] = useState(500);

  const handleTagInput = (e) => {
    setTagInput(e.target.value);
  };

  const handleTagSubmit = (e) => {
    e.preventDefault();
    setTags([...tags, tagInput]);
    setTagInput("");
    setShowTagInput(false);
  };

  const handleTagDelete = (index) => {
    const updatedTags = tags.filter((_, i) => i !== index);
    setTags(updatedTags);
  };

  const handleSearch = (e) => {
    e.preventDefault();
    const searchBody = {
        page : 1,
        pageSize: 6,
        searchType: "FIELD",
        params : {
            min_price : Number(min),
            max_price : Number(max),
            title : textInput,
            tag_list : tags
        }
    }

    CourseService.searchCourseByCondition(searchBody).then((res) => {
      sessionStorage.setItem("SEARCH_COURSE", JSON.stringify(searchBody) || {})
      sessionStorage.setItem("SEARCH_RESULT_LIST", JSON.stringify(res.data.data.datas) || [])
      sessionStorage.setItem("SEARCH_RESULT_COUNT", res.data.data.totalData || 0)

      eventEmitter.emit("LIST_DATA", JSON.stringify(res.data.data.datas) || []);
      eventEmitter.emit("SEARCH_BODY", JSON.stringify(searchBody) || {});
      eventEmitter.emit("DATA_COUNT", res.data.data.totalData || 0);
      
    })
  };

  return (
    <div className="left-section">
        <p>Search something...</p>
      <div className="input-row">
        <input
          type="text"
          value={textInput}
          onChange={(e) => setTextInput(e.target.value)}
          placeholder="Title..."
        />
      </div>
      <div className="input-row-flex">
        <p>Min</p>
        <input
          type="range"
          min={0}
          max={499}
          value={min}
          onChange={(e) =>
            setMin(e.target.value)
          }
        />
        <p>{min}</p>
      </div>
      <div className="input-row-flex">
        <p>Max</p>
        <input
          type="range"
          min={500}
          max={1000}
          value={max}
          onChange={(e) =>
            setMax(e.target.value)
          }
        />
        <p>{max}</p>
      </div>
      <div className="input-row">
      <p>Tags related</p>
        {tags.map((tag, index) => (
          <button
            key={index}
            className="tag-button"
            onClick={() => handleTagDelete(index)}
          >
            {tag}
          </button>
        ))}
        {showTagInput ? (
          <form onSubmit={handleTagSubmit}>
            <input
              type="text"
              value={tagInput}
              onChange={handleTagInput}
              onBlur={() => setShowTagInput(false)}
              autoFocus
            />
          </form>
        ) : (
          <button
            className="tag-button-add"
            onClick={() => setShowTagInput(true)}
          >
            +
          </button>
        )}
      </div>
      <div className="input-row">
        <button className="confirm" onClick={handleSearch}>
          Search
        </button>
      </div>
    </div>
  );
};
export default SearchSpace;

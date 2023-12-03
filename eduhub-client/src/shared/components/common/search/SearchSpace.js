import React, { useState } from "react";
import "./search.css";
import Slider from "react-slick/lib/slider";
import ReactSlider from "react-slider";

const SearchSpace = () => {
  const [textInput, setTextInput] = useState("");
  const [tags, setTags] = useState([]);
  const [tagInput, setTagInput] = useState("");
  const [showTagInput, setShowTagInput] = useState(false);
  const [rangeValues, setRangeValues] = useState([0, 1000]);

  const handleSliderChange = (newValues) => {
    setRangeValues(newValues);
  };
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

  const handleSearch = () => {
    console.log("Search clicked:", textInput, rangeValues, tags);
  };

  return (
    <div className="left-section">
      <div className="input-row">
        <input
          type="text"
          value={textInput}
          onChange={(e) => setTextInput(e.target.value)}
          placeholder="Title..."
        />
      </div>
      <ReactSlider
        thumbClassName="example-thumb"
        trackClassName="example-track"
        defaultValue={[0, 1000]}
        ariaLabel={["Lower thumb", "Upper thumb"]}
        ariaValuetext={(state) => `Thumb value ${state.valueNow}`}
        renderThumb={(props, state) => <div {...props}>{state.valueNow}</div>}
        pearling
        onAfterChange={handleSliderChange}
        minDistance={10}
      />
      {/* <Slider
        min={0}
        max={1000}
        defaultValue={[0, 1000]}
        value={rangeValues}
        onChange={handleSliderChange}
      /> */}
      <div className="input-row">
        <input
          type="range"
          min={0}
          max={1000}
          value={rangeValues}
          onChange={(e) =>
            setRangeValues(e.target.value.split(",").map(Number))
          }
        />
      </div>
      <div className="input-row">
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

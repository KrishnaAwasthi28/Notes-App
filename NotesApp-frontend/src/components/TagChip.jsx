import React from "react";
import { tagToColor } from "../utils/tagToColor.js";

const TagChip = ({ name }) => {
  return (
    <span className="tag" style={{ backgroundColor: tagToColor(name) }}>
      #{name}
    </span>
  );
};

export default TagChip;

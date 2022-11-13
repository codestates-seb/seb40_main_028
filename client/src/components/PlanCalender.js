import DatePicker from 'react-datepicker';
import { useState } from 'react';
import 'react-datepicker/dist/react-datepicker.css';

export default function PlanCalender() {
  const [startDate, setStartDate] = useState(new Date());
  return (
    <div className="flex items-center justify-center">
      <DatePicker
        locale="ko"
        dateFormat="yyyy.MM.dd(eee)"
        selected={startDate}
        onChange={(date) => setStartDate(date)}
      />
    </div>
  );
}

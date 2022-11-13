import { useNavigate } from 'react-router-dom';
import { cls } from '../assets/utils';

export default function Button({ text, onGoBack, large, onGoMain }) {
  const navigate = useNavigate();
  const goBack = () => {
    navigate(-1);
  };
  const goMain = () => {
    navigate('/');
  };
  return (
    <button
      onClick={onGoBack ? goBack : onGoMain ? goMain : null}
      className={cls(
        'px-14 py-2 bg-d-button hover:bg-d-button-hover text-white border border-transparent rounded-md shadow-sm font-medium',
        large ? 'text-xl' : 'text-lg'
      )}
    >
      {text}
    </button>
  );
}

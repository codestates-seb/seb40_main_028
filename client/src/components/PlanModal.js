import { useState } from 'react';
import React from 'react';
import Modal from 'react-modal';

const PlanModal = (props) => {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const style = {
    overlay: {
      position: 'fixed',
      top: 0,
      left: 0,
      right: 0,
      bottom: 0,
      backgroundColor: 'rgba(255, 255, 255, 0.75)',
    },
    content: {
      position: 'absolute',
      top: '40px',
      left: '40px',
      right: '40px',
      bottom: '40px',
      border: '1px solid #ccc',
      background: '#fff',
      overflow: 'auto',
      WebkitOverflowScrolling: 'touch',
      borderRadius: '4px',
      outline: 'none',
      padding: '20px',
    },
  };

  return (
    <Modal
      isOpen={isModalOpen}
      style={style}
      appElement={document.getElementById('root')}
    >
      hi
    </Modal>
  );
};

export default PlanModal;

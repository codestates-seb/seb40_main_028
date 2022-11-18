import { useEffect, useState } from 'react';
import Layout from '../components/Layout';
import PlanCalendar from '../components/PlanCalendar';
import PlanList from '../components/PlanList';
import PlanAdd from '../components/PlanAdd';
import PlanModal from '../components/PlanModal';
import { useRecoilState } from 'recoil';
import { ModalNum, isModal } from '../state/states';

const Plan = () => {
  const [isModalOpen, setIsModalOpen] = useRecoilState(isModal);
  const [categories, setCategories] = useState(null);
  const [Modals, setModals] = useRecoilState(ModalNum);

  useEffect(() => {
    if (!isModalOpen) {
      setModals(0);
    }
  }, [isModalOpen]);

  return (
    <div className="h-screen bg-d-lighter">
      <Layout title="계획작성" hasTabBar>
        <div className="flex flex-col h-full items-center mt-5">
          <PlanCalendar />
        </div>
        <PlanList />
        <div className="items-center max-w-lg bg-d-lighter fixed bottom-[4.5em] w-full px-11 pb-5 pt-5 flex">
          <PlanAdd
            setIsModalOpen={setIsModalOpen}
            setCategories={setCategories}
          />
        </div>
        {isModalOpen && (
          <PlanModal
            isModalOpen={isModalOpen}
            setIsModalOpen={setIsModalOpen}
          />
        )}
      </Layout>
    </div>
  );
};

export default Plan;

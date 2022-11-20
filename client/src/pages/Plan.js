import { useEffect, useState } from 'react';
import Layout from '../components/Layout';
import PlanCalendar from '../components/Plan/PlanCalendar';
import PlanList from '../components/Plan/PlanList';
import PlanAdd from '../components/Plan/PlanAdd';
import PlanModal from '../components/Plan/Modals/PlanModal';
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
    <div className="h-screen bg-d-lighter relative">
      <Layout title="계획작성" hasTabBar>
        <div className="flex flex-col h-full items-center mt-5">
          <PlanCalendar />
        </div>
        <PlanList />

        <PlanAdd
          setIsModalOpen={setIsModalOpen}
          setCategories={setCategories}
        />

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

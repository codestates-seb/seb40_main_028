import Layout from '../components/Layout';
import PlanCalendar from '../components/PlanCalendar';
import PlanList from '../components/PlanList';
import PlanAdd from '../components/PlanAdd';
import PlanModal from '../components/PlanModal';

const Plan = () => {
  return (
    <div className="h-screen bg-d-lighter">
      <Layout title="계획작성" hasTabBar>
        <div className="flex flex-col h-full items-center mt-5">
          <PlanCalendar />
        </div>
        <PlanList />
        <div className="items-center max-w-lg bg-d-lighter fixed bottom-[4.5em] w-full px-11 pb-5 pt-5 flex">
          <PlanAdd />
        </div>
        <PlanModal />
      </Layout>
    </div>
  );
};

export default Plan;

"use client";

import React from "react";
import { motion } from "framer-motion";

interface CardProps {
  children: React.ReactNode;
  className?: string;
  hoverable?: boolean;
}

export const Card: React.FC<CardProps> = ({
  children,
  className = "",
  hoverable = false,
}) => {
  const baseStyle = "bg-zinc-900/60 backdrop-blur-md rounded-lg border border-zinc-800/80 p-5 shadow-xl";
  const hoverStyle = hoverable ? "hover:border-indigo-500/30 transition-all duration-300 hover:shadow-[0_0_20px_rgba(99,102,241,0.1)] cursor-pointer" : "";

  return (
    <motion.div
      initial={{ opacity: 0, y: 10 }}
      animate={{ opacity: 1, y: 0 }}
      transition={{ duration: 0.3 }}
      className={`${baseStyle} ${hoverStyle} ${className}`}
    >
      {children}
    </motion.div>
  );
};

export const CardHeader: React.FC<{ children: React.ReactNode; className?: string }> = ({
  children,
  className = "",
}) => <div className={`mb-4 border-b border-zinc-800/60 pb-3 ${className}`}>{children}</div>;

export const CardTitle: React.FC<{ children: React.ReactNode; className?: string }> = ({
  children,
  className = "",
}) => <h3 className={`text-lg font-bold tracking-tight text-white ${className}`}>{children}</h3>;

export const CardBody: React.FC<{ children: React.ReactNode; className?: string }> = ({
  children,
  className = "",
}) => <div className={`text-sm text-zinc-300 leading-relaxed ${className}`}>{children}</div>;
